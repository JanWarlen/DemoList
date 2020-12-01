package com.janwarlen.util;

import java.io.Serializable;

/**
 *
 * @param <T>
 */
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 45387487319877474L;

    /**
     * 返回数据，如果请求失败，则为空
     */
    private T data;

    /**
     * 成功标记
     */
    private Boolean success = true;


    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 错误Code
     */
    private int code = 200;



    public Response(T data) {
        this.data = data;
    }


    public Response(int code, String errorMsg){
        this.errorMsg = errorMsg;
        this.code = code;
    }

    private Response(){

    }

    public static Response defaultResponse(){
        return new Response();
    }

    public Boolean getSuccess() {
        if(code != 200){
            return false;
        }
        return success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public int getCode() {
        return code;
    }
}
