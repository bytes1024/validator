package cn.bytes1024.validator;

import cn.bytes1024.validator.annotation.Invoker;
import cn.bytes1024.validator.exception.ValidatorException;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * All rights Reserved
 * 验证拦截
 * @author maliang
 * @date 2019/1/26 13:09
 * @version V1.0
 */
public class ValidatorHelper {

    private javax.validation.Validator validator;

    public ValidatorHelper(javax.validation.Validator validator) {
        this.validator = validator;
    }

    public void invoker(Object object) throws ValidatorException {

        if(Objects.isNull(object)){
            return;
        }

        this.invokerFieldThrows(object);
        this.invokerMethodThrows(object);
    }





    /**
     * 返回结果方式
     * @param object 目标
     * @return map对象
     */
    public Map<Path,String> invokerFieldReturn(Object object) {
        if(object == null )
        {
            return null;
        }

        Set<ConstraintViolation<Object>> results = validator.validate(object);
        if(results==null || results.isEmpty()){
            return null;
        }
        return results
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(ConstraintViolation::getPropertyPath,ConstraintViolation::getMessage));
    }

    /**
     * 抛出异常的方式
     * @param object 目标
     * @throws ValidatorException 异常
     */
    public void invokerFieldThrows(Object object) throws ValidatorException{
        Map<Path,String> results = this.invokerFieldReturn(object);
        this.throwFormat(results);
    }

    private void throwFormat(Map<Path, String> results) throws ValidatorException {
        if(results!=null && !results.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<Path, String> entry : results.entrySet()) {
                String path = entry.getKey().toString();
                String value = entry.getValue();
                sb.append(path).append(":").append(value).append(";");
            }
            throw new ValidatorException(sb.toString());
        }
    }

    /**
     * 执行添加了@Invoker 注解的方法
     * @param object object
     * @throws ValidatorException 异常
     */
    public void invokerMethodThrows(Object object) throws ValidatorException{
        this.invokerMethod(object,getMethods(object),0);
    }

    private void invokerMethod(Object target, List<Method> methods, int index) throws ValidatorException {
        if(target==null){
            return;
        }
        if(methods!=null && !methods.isEmpty()) {
            if(index>=methods.size()){
                return;
            }
            Method method = methods.get(index);
            try {
                method.invoke(target,new Object[]{});
            } catch (Exception e) {
                String message = "invoker error";
                if(e instanceof InvocationTargetException) {
                    InvocationTargetException ite = (InvocationTargetException)e;
                    Throwable throwable = ite.getTargetException();
                    if(throwable!=null ) {
                        message = throwable.getMessage();
                    }
                }
                throw new ValidatorException(message);
            }
            index++;
            invokerMethod(target,methods,index);
        }
    }

    private List<Method> getMethods(Object target) {
        Class clazz = target.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> filterMethods = filterSort(methods);
        return filterMethods;
    }

    private List<Method> filterSort(Method[] methods) {
        if(methods==null || methods.length<=0){
            return null;
        }
        List<Method>  resultMethods = Stream.of(methods)
                .filter(m-> m.isAnnotationPresent(Invoker.class) && Modifier.isPublic(m.getModifiers())).collect(Collectors.toList());
        AnnotationAwareOrderComparator.sort(resultMethods);
        return resultMethods;
    }

}
