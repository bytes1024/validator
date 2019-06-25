package cn.bytes1024.validator.annotation;

import java.lang.annotation.*;

/**
 * 标记方法注解
 * <p>
 * 对于非过滤的校验对象，会执行当前注解标注的方法，方法是自定义校验方法，例如
 * <br>
 * public void validatorTest(){<br>
 * if(age>3){<br>
 * throw new RuntimeException();<br>
 * }
 * if(age>4 && null!=email){<br>
 * //todo<br>
 * }<br>
 * }<br>
 * </p>
 *
 * @author 江浩
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Invoker {
}
