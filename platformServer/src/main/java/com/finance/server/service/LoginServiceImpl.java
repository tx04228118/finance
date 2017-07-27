/*
 * Copyright (C), 2013-2017, 上汽集团
 * FileName: LoginService.java
 * Author:   pengxiaofei
 * Date:     2017年7月24日 下午1:25:52
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.finance.server.service;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.finance.server.anno.ServiceThriftAnno;
import com.finance.server.entity.BizUserEntity;
import com.finance.service.interfaces.LoginService;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author pengxiaofei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
@ServiceThriftAnno(serviceName = "loginService")
public class LoginServiceImpl implements LoginService.Iface {

    /* (non-Javadoc)
     * @see com.finance.service.interfaces.LoginService.Iface#switchData(java.lang.String, java.lang.String)
     */
    @Autowired 
    private MongoTemplate mongoTemplate;
    
    @Override
    public String switchData(String username, String passWord) throws TException {
       
        BizUserEntity user =   mongoTemplate.findOne(new Query(Criteria.where("userName").is(username)), BizUserEntity.class);
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip = addr.getHostAddress().toString();
        if(user==null){
            return "查无此用户"; 
        }else{
            return "用户姓名为："+user.getRealName()+"ip为："+ip;
        }
        
    }

}
