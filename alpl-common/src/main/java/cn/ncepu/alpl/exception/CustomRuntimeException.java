package cn.ncepu.alpl.exception;

/**
 * @author xufuhang
 * @date 2022/5/7-13:24
 */
public class CustomRuntimeException extends RuntimeException{

    public CustomRuntimeException() { }

    public CustomRuntimeException(String message) {
        super(message);
    }

}
