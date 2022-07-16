package cn.ncepu.alpl.api;

import lombok.Getter;

/*
@author xufuhang
@date 2021/9/14-0:19
*/
@Getter
public enum ResultEnum {

    SUCCESS(200, "操作成功"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    VALIDATE_FAILED(404, "参数检验失败"),
    AUTHENTICATE_FAILED(401, "用户名或密码错误"),
    CLIENT_ERROR(450, "客户端错误"),
    FAILED(500, "操作失败"),
    SERVER_ERROR(550, "服务器繁忙"),

    WAITING(30000, "等待中，请稍后查询"),

    ADD_CONTENT_SUCCESS(20000, "发布成功"),
    UPDATE_CONTENT_SUCCESS(20001, "修改成功"),
    DELETE_CONTENT_SUCCESS(20002, "删除成功"),
    QUERY_CONTENT_SUCCESS(20003, "查询成功"),
    ADD_CONTENT_FAILED(50000, "发布失败"),
    UPDATE_CONTENT_FAILED(50001, "修改失败"),
    DELETE_CONTENT_FAILED(50002, "删除失败"),
    QUERY_CONTENT_FAILED(50003, "查询失败");

    private long code;
    private String message;

    ResultEnum(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
