package com.raxcl.blog.common.aop;

import java.lang.annotation.*;

//Type 代表可以放在类上面，method代表可以放在方法上面
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    //模块
    String module() default "";
    //方法
    String operator() default  "";
}
