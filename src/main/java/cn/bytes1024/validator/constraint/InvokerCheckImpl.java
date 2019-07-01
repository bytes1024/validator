package cn.bytes1024.validator.constraint;

import cn.bytes1024.validator.annotation.Invoker;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * invoker 注解自定义处理
 *
 * @author 江浩
 */
public class InvokerCheckImpl implements ConstraintValidator<Invoker, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return false;
    }
}
