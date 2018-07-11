package com.xmasye.ours.constance;

/**
 * 功能：
 * 详情：
 *
 * @author yezhiwei@ibeiliao.com
 * @since 2017年10月13日
 */
public class ApiResult {

    private int errcode;
    private String errmsg;
    private Object data;

    private ApiResult() {
    }

    private ApiResult(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    private ApiResult(int errcode, String errmsg, Object data) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.data = data;
    }

    public static ApiResult build(int errcode, String errmsg){
        return new ApiResult(errcode, errmsg);
    }

    public static ApiResult build(int errcode, String errmsg, Object data){
        return new ApiResult(errcode, errmsg, data);
    }

    public static ApiResult build(ErrorCode errorCode){
        return new ApiResult(errorCode.getCode(), errorCode.getMsg());
    }

    public static ApiResult build(ErrorCode errorCode, Object data){
        return new ApiResult(errorCode.getCode(), errorCode.getMsg(), data);
    }

    public static ApiResult buildSuccess(){
        return ApiResult.build(ErrorCode.SUCCESS);
    }

    public static ApiResult buildSuccess(Object data){
        ApiResult apiResult = ApiResult.build(ErrorCode.SUCCESS);
        apiResult.setData(data);
        return apiResult;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
