/*
 * Copyright (C), 2013-2017, 上汽集团
 * FileName: ThriftTestServiceImpl.java
 * Author:   pengxiaofei
 * Date:     2017年7月26日 上午10:39:31
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.finance.server.service;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.finance.server.anno.ServiceThriftAnno;
import com.finance.service.interfaces.ThriftTestService;
/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author pengxiaofei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
@ServiceThriftAnno(serviceName = "thriftTestService")
public class ThriftTestServiceImpl implements ThriftTestService.Iface {

    /* (non-Javadoc)
     * @see com.finance.service.interfaces.ThriftTestService.Iface#switchData(java.lang.String, java.lang.String)
     */
    @Override
    public String switchData(String username, String passWord) throws TException {
        
        System.out.println("参数："+username);
        
        InetAddress addr = null;
        try {
            
            Thread.sleep(1000*120);
            
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String ip = addr.getHostAddress().toString();
            return "这是测试：ip为："+ip;
        
    }

}
