package com.xmasye.ours.constance;


/**
 * 功能：error code
 *
 * 详细：
 *
 */
public enum ErrorCode {

    SUCCESS(0, "成功"),
    FAILURE(1, "失败"),
    EXCEPTION(2, "系统异常"),
    ACCESS_INVALID(3, "无权限操作，请先登录"),
    PARAMETER_ERROR(10, "参数错误"),
    UNKONW_ERROR(20, "未知错误");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
