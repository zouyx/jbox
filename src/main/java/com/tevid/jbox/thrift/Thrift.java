package com.tevid.jbox.thrift;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Thrift {

    //thrift服务名
    String serviceName() default "";
}
