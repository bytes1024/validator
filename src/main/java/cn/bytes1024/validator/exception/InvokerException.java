package cn.bytes1024.validator.exception;

/**
 * 异常
 *
 * @author 江浩
 */
public class InvokerException extends RuntimeException {

    private String key;

    private String message;

    public InvokerException(String key, String message) {
        super(new ValidatorException(String.format("%s:%s", key, message)));
        this.key = key;
        this.message = String.format("%s:%s", key, message);
    }

    public String getKey() {
        return key;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
