package cn.bytes1024.validator.exception;

/**
 * 验证异常
 *
 * @author 江浩
 */
public class ValidatorException extends RuntimeException {

    private String message;

    public ValidatorException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
