package com.tevid.jbox.controller.response;


import com.tevid.jbox.contant.Const;

import java.time.Instant;

/**
 * 返回实体
 * Created by joezou on 2016/11/8.
 */
public class Return {
    public Return() {
    }
    public Return(int code) {
        this(code, Const.RETURN_MSG.get(code));
    }

    public Return(int code, String msg) {
        this.code = code;
        this.time = Instant.now().getEpochSecond();
        this.msg = msg;
    }

    /**
     * 	接口状态（0:正常）
     */
    private int code;

    /**
     * 服务器时间 单位:秒
     */
    private long time;

    /**
     * 接口错误信息(用于前端提示)
     */
    private String msg;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取错误返回
     * @param code
     * @return
     */
    public static Return genReturn(int code){
        return new Return(code);
    }

    /**
     * 获取错误返回
     * @param code
     * @return
     */
    public static Return genErrorReturn(int code, String msg){
        return new Return(code,msg);
    }

    /**
     * 获取正确返回
     * @return
     */
    public static Return genSuccessReturn(){
        return genReturn(Const.RETURN_CODE.SUCCESS.value);
    }

    @Override
    public String toString() {
        return "Return{" +
                "code=" + code +
                ", time=" + time +
                ", msg='" + msg + '\'' +
                '}';
    }
}
