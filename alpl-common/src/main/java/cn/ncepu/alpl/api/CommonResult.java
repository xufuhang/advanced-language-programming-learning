package cn.ncepu.alpl.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
@author xufuhang
@date 2021/9/14-0:12
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private long code;
    private String message;
    private T data;

    /**
     * 构造通用返回对象
     * @return 通用返回对象
     */
    public static CommonResult<Void> success() {
        return new CommonResult<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 构造通用返回对象
     * @param data 返回数据
     * @param <T> 数据类型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 构造通用返回对象
     * @param data 返回数据
     * @param message 提示消息
     * @param <T> 数据类型
     * @return 通用返回对象
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> CommonResult<T> failed() {
        return failed(ResultEnum.FAILED);
    }

    public static <T> CommonResult<T> failed(ResultEnum resultEnum) {
        return new CommonResult<T>(resultEnum.getCode(), resultEnum.getMessage(), null);
    }

    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultEnum.FAILED.getCode(), message, null);
    }

    public static <T> CommonResult<T> failed(ResultEnum resultEnum, String message) {
        return new CommonResult<T>(resultEnum.getCode(), message, null);
    }




    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultEnum.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultEnum.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultEnum.UNAUTHORIZED.getCode(), ResultEnum.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultEnum.FORBIDDEN.getCode(), ResultEnum.FORBIDDEN.getMessage(), data);
    }

}
