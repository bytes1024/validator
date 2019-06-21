package cn.bytes1024.validator.annotation;

import cn.bytes1024.validator.configuration.AutoEnableParamValidatorImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * All rights Reserved
 * <p>标识是否开启参数校验</p>
 * @author maliang
 * @date 2019/1/26 11:52
 * @version V1.0
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoEnableParamValidatorImportSelector.class)
public @interface EnableParamValidator {
}
