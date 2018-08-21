package com.common.service.util;

public class ResponseBean {
    //http状态码
    private  int code;
    //返回信息
    private  Object data;
    //返回数据
    private  String msg="error";
    ResponseBean(){

    }

    public ResponseBean(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
