package com.finance.server.conf;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class ZooKeeperConfig {

	@Value("${service.name}")
	String serviceName;
	
	@Value("${zookeeper.server.list}")
	String serverList;

    @Value("${thrift-port}")
    int thriftPort;
	
	ExecutorService executor = Executors.newSingleThreadExecutor();
	
	
	public void init(){
		executor.execute(new Runnable() {			
			@Override
			public void run() {	
			    System.out.println("开始注册服务");
				registService();				
				try {
					Thread.sleep(1000*60*60*24*360*10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// 注册服务
	public ZkClient registService() {
		String servicePath = "/"+serviceName ;// 根节点路径
		ZkClient zkClient = new ZkClient(serverList);
		boolean rootExists = zkClient.exists(servicePath);
		if (!rootExists) {
			zkClient.createPersistent(servicePath);
		}
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String ip = addr.getHostAddress().toString();
		// 注册当前服务
		for (int i = 0; i < ConfConstants.REGISTLIST.size(); i++) {
		    String serviceInstance = ip +"-"+thriftPort+"-"+ConfConstants.REGISTLIST.get(i) ;
		    zkClient.createEphemeral(servicePath + "/" + serviceInstance);
		    System.out.println("提供的服务为：" + servicePath + "/" + serviceInstance);
        }
		
		
		
		return zkClient;
	}
	
	
	
}
