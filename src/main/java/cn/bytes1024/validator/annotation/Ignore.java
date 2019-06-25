package cn.bytes1024.validator.annotation;

import java.lang.annotation.*;

/**
 * 忽略标记
 * <p>
 * 1.如果该注解标记在被校验类上面，那么所有涉及该类的校验都会忽略 <br />
 * 2.如果该标记标记在方法参数前面，那么对应的该参数将不会被校验
 * </p>
 *
 * @author 江浩
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Inherited
public @interface Ignore {
}
