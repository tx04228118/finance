package com.finance.client.service;

import java.util.Map;
import java.util.Random;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.springframework.stereotype.Component;

import com.finance.client.conf.ZooKeeperConfig;
import com.finance.service.interfaces.LoginService;

@Component
public class LoginServiceProvider {
    
   
	
	public Object  getBalanceClient(String serverName){
	    Map<String,Object> serviceMap =ZooKeeperConfig.serviceMap.get(serverName);
	    for (Map.Entry<String, Object> entry : serviceMap.entrySet()) {
            System.out.println("可供选择服务:"+entry.getKey());
        }
	    int rand=new Random().nextInt(serviceMap.size());      
        String[] mkeys = serviceMap.keySet().toArray(new String[serviceMap.size()]);
        return serviceMap.get(mkeys[rand]);
	}
	

}
