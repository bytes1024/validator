package cn.bytes1024.validator.annotation;

import java.lang.annotation.*;

/**
 * 标记需要拦截的对象
 * <p>
 * 1.如果该注解标注在类上面，那么调用该类的方法将会执行拦截过程
 * 2.如果该注解标注在方法上面，那么只会在执行该方法的时候调用拦截
 * </p>
 *
 * @author 江浩
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface Proof {
}
