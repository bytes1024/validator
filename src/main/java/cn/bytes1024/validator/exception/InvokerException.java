package cn.bytes1024.validator.exception;


/**
 * All rights Reserved
 * @author maliang
 * @date 2019/1/26 14:53
 * @version V1.0
 */
public class InvokerException extends RuntimeException{

    private String key;

    private String message;

    public InvokerException(String key,String message) {
        super(new ValidatorException(String.format("%s:%s",key,message)));
        this.key = key;
        this.message = String.format("%s:%s",key,message);
    }

    public String getKey() {
        return key;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
