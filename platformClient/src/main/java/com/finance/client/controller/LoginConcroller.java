/*
 * Copyright (C), 2013-2017, 上汽集团
 * FileName: LoginConcroller.java
 * Author:   pengxiaofei
 * Date:     2017年7月24日 下午2:18:03
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.finance.client.controller;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.finance.client.service.LoginServiceProvider;
import com.finance.service.interfaces.LoginService;
import com.finance.service.interfaces.ThriftTestService;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author pengxiaofei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
public class LoginConcroller {
    

    @Autowired
    LoginServiceProvider loginServiceProvider;
    
    @ResponseBody
    @RequestMapping(value = "/hello")
    String hello() throws TException {
        LoginService.Client svr  = (LoginService.Client )loginServiceProvider.getBalanceClient("loginService");
        
         String str = svr.switchData("cx_tester", "11111");
        return "hi "+str;
    }
    
    @ResponseBody
    @RequestMapping(value = "/test")
    String test() throws TException {
        ThriftTestService.Client svr  = (ThriftTestService.Client )loginServiceProvider.getBalanceClient("thriftTestService");
        
         String str = svr.switchData("cx_tester", "11111");
        return "hi "+str;
    }


}
