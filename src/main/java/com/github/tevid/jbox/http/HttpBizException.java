package com.github.tevid.jbox.http;

import com.github.tevid.jbox.exception.BizException;

public class HttpBizException extends BizException {

    public HttpBizException(int status) {
        super(status);
    }

    public HttpBizException(int status, String message) {
        super(status, message);
    }
}
