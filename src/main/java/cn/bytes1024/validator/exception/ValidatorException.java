package cn.bytes1024.validator.exception;

/**
 *  TODO使用全局异常
 * @author admin
 */
public class ValidatorException extends RuntimeException {

    private String message;

    public ValidatorException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
