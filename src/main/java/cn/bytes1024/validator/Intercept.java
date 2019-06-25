package cn.bytes1024.validator;


import cn.bytes1024.validator.annotation.Ignore;
import cn.bytes1024.validator.exception.ValidatorException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * aop 拦截
 *
 * @author 江浩
 */
@Aspect
@Slf4j
public class Intercept {


    private ValidatorHelper validatorHelper;

    public Intercept(ValidatorHelper validatorHelper) {
        this.validatorHelper = validatorHelper;
    }

    /**
     * 切入目标对象
     *
     * @param joinPoint joinPoint
     * @throws ValidatorException 抛出异常
     */
    @Before(value = "@within(cn.bytes1024.validator.annotation.Proof) || @annotation(cn.bytes1024.validator.annotation.Proof)")
    public void invoker(JoinPoint joinPoint) throws ValidatorException {
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            Parameter[] parameters = method.getParameters();

            targetInvoker(parameters, joinPoint.getArgs(), 0);
        }
    }

    /**
     * 目标验证对象操作
     *
     * @param parameters 检查对象
     * @throws ValidatorException 抛出异常
     */
    private void targetInvoker(Parameter[] parameters, Object[] objects, int index) throws ValidatorException {

        if (Objects.isNull(parameters) || parameters.length <= 0 || index >= parameters.length) {
            return;
        }
        Parameter parameter = parameters[index];
        Object object = objects[index];

        if (!Objects.isNull(object)) {
            Ignore ignore = parameter.getAnnotation(Ignore.class);
            if (Objects.isNull(ignore) && !object.getClass().isAnnotationPresent(Ignore.class)) {
                validatorHelper.invoker(object);
            }
        }

        this.targetInvoker(parameters, objects, ++index);

    }


}
