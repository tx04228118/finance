/*
 * Copyright (C), 2013-2017, 上汽集团
 * FileName: ServiceThriftAnno.java
 * Author:   pengxiaofei
 * Date:     2017年7月24日 下午2:26:09
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.finance.server.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface ServiceThriftAnno {
    public abstract String serviceName();
}
