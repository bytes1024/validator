package cn.bytes1024.validator.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.PARAMETER})
@Inherited
public @interface Ignore {
}
