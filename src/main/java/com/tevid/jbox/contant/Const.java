package com.tevid.jbox.contant;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by joezou on 2016/12/12.
 */
public class Const {

    //每页数量
    public static final int PAGE_SIZE=100;
    //导入数据开始页码
    public static final int START_PAGE=1;

    /**
     * 返回码
     */
    public enum RETURN_CODE{
        SUCCESS(0),
        FAIL_UNKNOWN(1),
        HTTP_FAIL(3);

        public int value;
        RETURN_CODE(int value){
            this.value=value;
        }
    }

    public static Map<Integer,String> RETURN_MSG= Maps.newHashMap();

    static {
        RETURN_MSG.put(RETURN_CODE.SUCCESS.value,"成功!");
        RETURN_MSG.put(RETURN_CODE.FAIL_UNKNOWN.value,"失败!未知异常");
        RETURN_MSG.put(RETURN_CODE.HTTP_FAIL.value,"http链接!");
    }

}
