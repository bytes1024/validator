package cn.bytes1024.validator.annotation;

import java.lang.annotation.*;

/**
 * 拦截调用
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.TYPE,ElementType.METHOD})
@Inherited
public @interface Proof {
}
