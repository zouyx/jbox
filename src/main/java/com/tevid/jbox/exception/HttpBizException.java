package com.tevid.jbox.exception;

public class HttpBizException extends BizException{

    public HttpBizException(int status) {
        super(status);
    }

    public HttpBizException(int status, String message) {
        super(status, message);
    }
}
