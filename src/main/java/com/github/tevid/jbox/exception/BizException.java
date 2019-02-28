package com.github.tevid.jbox.exception;


import static com.github.tevid.jbox.contant.Const.RETURN_MSG;

public class BizException extends Exception{
    private int status;

    public BizException(int status) {
        super(RETURN_MSG.get(status));
        this.status = status;
    }

    public BizException(int status,String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
