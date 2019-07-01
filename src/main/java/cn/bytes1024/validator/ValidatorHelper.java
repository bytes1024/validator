package cn.bytes1024.validator;

import cn.bytes1024.validator.annotation.Invoker;
import cn.bytes1024.validator.exception.ValidatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 具体验证拦截执行
 *
 * @author 江浩
 */
@Slf4j
public class ValidatorHelper {

    private javax.validation.Validator validator;

    public ValidatorHelper(javax.validation.Validator validator) {
        this.validator = validator;
    }

    /**
     * 验证当前对象
     * <p>
     * </p>
     *
     * @param object :
     * @return : void
     * @author 江浩
     */
    public void invoker(Object object) throws ValidatorException {
        Map<Path, String> results = this.invokerFieldReturn(object);
        this.throwExceptionFormat(results);
        //对应的方法
        this.invokerMethodThrows(object);

    }


    /**
     * 返回结果方式
     *
     * @param object 目标
     * @return map对象
     */
    public Map<Path, String> invokerFieldReturn(Object object) {
        if (object == null) {
            return null;
        }

        Set<ConstraintViolation<Object>> results = validator.validate(object);
        if (results == null || results.isEmpty()) {
            return null;
        }
        return results
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }


    private void throwExceptionFormat(Map<Path, String> results) throws ValidatorException {
        if (results != null && !results.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Path, String> entry : results.entrySet()) {
                String path = entry.getKey().toString();
                String value = entry.getValue();
                sb.append(path).append(":").append(value).append(";");
            }
            throw new ValidatorException(sb.toString());
        }
    }

    /**
     * 执行添加了@Invoker 注解的方法
     *
     * @param object object
     * @throws ValidatorException 异常
     */
    public void invokerMethodThrows(Object object) throws ValidatorException {

        /**
         * 当前对象标识@Invoker的方法
         */
        this.invokerMethod(object, getMethods(object), 0);

        /**
         * 当前对象对应属性的@Invoker方法
         */
        try {
            Object[] tagFields = this.getTagFields(object);
            if (!Objects.isNull(tagFields)) {
                for (Object tagField : tagFields) {
                    this.invokerMethodThrows(tagField);
                }
            }
        } catch (IllegalAccessException e) {
            log.error("invoker method error: {}", e);
        }

    }

    /**
     * 获取所有标识valid注解的属性
     *
     * @param object :
     * @return : java.lang.Object[]
     * @author 江浩
     */
    private Object[] getTagFields(Object object) throws IllegalAccessException {

        Field[] fields = object.getClass().getDeclaredFields();
        if (!Objects.isNull(fields) && fields.length > 0) {
            List<Object> objects = new ArrayList<>(fields.length);
            for (Field field : fields) {
                if (field.isAnnotationPresent(Valid.class)) {
                    field.setAccessible(true);
                    Object fieldValue = field.get(object);
                    if (!Objects.isNull(fieldValue)) {
                        objects.add(fieldValue);
                    }
                    field.setAccessible(false);
                }
            }
            return objects.toArray();
        }
        return null;
    }

    private void invokerMethod(Object target, List<Method> methods, int index) throws ValidatorException {
        if (target == null) {
            return;
        }
        if (methods != null && !methods.isEmpty()) {
            if (index >= methods.size()) {
                return;
            }
            Method method = methods.get(index);
            try {
                method.invoke(target, new Object[]{});
            } catch (Exception e) {
                String message = "invoker error";
                if (e instanceof InvocationTargetException) {
                    InvocationTargetException ite = (InvocationTargetException) e;
                    Throwable throwable = ite.getTargetException();
                    if (throwable != null) {
                        message = throwable.getMessage();
                    }
                }
                throw new ValidatorException(message);
            }
            index++;
            invokerMethod(target, methods, index);
        }
    }

    private List<Method> getMethods(Object target) {
        Class clazz = target.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> filterMethods = filterSort(methods);
        return filterMethods;
    }

    private List<Method> filterSort(Method[] methods) {
        if (methods == null || methods.length <= 0) {
            return null;
        }
        List<Method> resultMethods = Stream.of(methods)
                .filter(m -> m.isAnnotationPresent(Invoker.class) && Modifier.isPublic(m.getModifiers())).collect(Collectors.toList());
        AnnotationAwareOrderComparator.sort(resultMethods);
        return resultMethods;
    }

}
