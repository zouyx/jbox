package com.github.tevid.jbox.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.tevid.jbox.contant.Const;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class RestReturn<T> extends Return {
    public RestReturn() {
    }

    private T data;

    public RestReturn(T data) {
        this(Const.RETURN_CODE.SUCCESS.value,data);
    }

    public RestReturn(int code, T data) {
        super(code);
        this.data=data;
    }
    public RestReturn(int code, String msg, T data){
        super(code,msg);
        this.data=data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
