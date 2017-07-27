/*
 * Copyright (C), 2013-2017, 上汽集团
 * FileName: ConfConstants.java
 * Author:   pengxiaofei
 * Date:     2017年7月25日 下午2:14:34
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.finance.client.conf;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author pengxiaofei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ConfConstants {
    
    public static  Map<String, ConnectEntity> SERVICENAMELIST = new HashMap();

    public static final Map<String, String> SERVICEAPILIST = new HashMap();

    static {
        SERVICEAPILIST.put("loginService", "com.finance.service.interfaces.LoginService");
        SERVICEAPILIST.put("thriftTestService", "com.finance.service.interfaces.ThriftTestService");
    }
}
