package com.finance.client.conf;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.finance.service.interfaces.LoginService;



@Configuration
public class ZooKeeperConfig {
	

    
	@Value("${service.name}")
	String serviceName;

	@Value("${zookeeper.server.list}")
	String zookeeperList;

	ExecutorService executor = Executors.newSingleThreadExecutor();
	public static Map<String, TSocket> transportMap = new HashMap<String, TSocket>();

	public static Map<String, Map<String,Object>> serviceMap = new HashMap<String, Map<String,Object>>();
	// thrift实例列表
	

	@PostConstruct
	private void init() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				startZooKeeper();
				try {
					Thread.sleep(1000 * 60 * 60 * 24 * 360 * 10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 注册服务
	private void startZooKeeper() {
		List<String> currChilds = new ArrayList<String>();
		String servicePath = "/" + serviceName;// 根节点路径
		ZkClient zkClient = new ZkClient(zookeeperList);
		boolean serviceExists = zkClient.exists(servicePath);
		if (serviceExists) {
			currChilds = zkClient.getChildren(servicePath);
		} else {
			throw new RuntimeException("service not exist!");
		}

		for (String instanceName : currChilds) {
			// 没有该服务，建立该服务
		    
		    String[] array = instanceName.split("-");
		    
			if (!serviceMap.containsKey(array[2])) {
			    Object ob  = createUserService(instanceName);
			    Map<String,Object> map =  new HashMap<String,Object>();
			    map.put(instanceName, ob);
				serviceMap.put(array[2], map);
			}else{
			    Map<String,Object> map = serviceMap.get(array[2]);
			    if(!map.containsKey(instanceName)){
			        Object ob  = createUserService(instanceName);
			        map.put(instanceName, ob);
	                serviceMap.put(array[2], map);
			    }
			}
		}
		// 注册事件监听
		zkClient.subscribeChildChanges(servicePath, new IZkChildListener() {
			// @Override
			public void handleChildChange(String parentPath,
					List<String> currentChilds) throws Exception {
				// 实例(path)列表:当某个服务实例宕机，实例列表内会减去该实例
				for (String instanceName : currentChilds) {
				    String[] array = instanceName.split("-");
					// 没有该服务，建立该服务
					if (!serviceMap.containsKey(array[2])) {
					    Object ob  = createUserService(instanceName);
		                Map<String,Object> map =  new HashMap<String,Object>();
		                map.put(instanceName, ob);
		                serviceMap.put(array[2], map);
					}else{
					    Map<String,Object> map = serviceMap.get(array[2]);
		                if(!map.containsKey(instanceName)){
		                    Object ob  = createUserService(instanceName);
		                    map.put(instanceName, ob);
		                    serviceMap.put(array[2], map);
		                }
					}
				}
				
				for (Map.Entry<String,Map<String,Object>> entry : serviceMap.entrySet()) {
                    
				    Map<String,Object> map = serviceMap.get(entry.getKey());
				    
				    for (Map.Entry<String,Object> entry1 : map.entrySet()) {
				        
                        if(!currentChilds.contains(entry1.getKey())){
                            TServiceClient  c=    (TServiceClient) map.get(entry1.getKey());
                            try {
                                c.getInputProtocol().getTransport().close();
                                c.getOutputProtocol().getTransport().close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            map.remove(entry1.getKey());
                        }
                    }
				    serviceMap.put(entry.getKey(), map);
                }
				
				System.out.println(parentPath + "事件触发");
			}
		});
	}

	// 创建一个服务实例
	private Object createUserService(String serviceInstanceName) {
		String ip = serviceInstanceName.split("-")[0];
		int port = Integer.parseInt(serviceInstanceName.split("-")[1]);
		String serverName = serviceInstanceName.split("-")[2];
		TSocket transport = null;
		
		Object client  = null;
		 
		
		try {
		    if(transportMap.containsKey(ip+"-"+port)){
	            transport = transportMap.get(ip+"-"+port);
	            
	        }else{
	            transport = new TSocket(ip, port);
	            
	        }
		    
		    if(!transport.isOpen()){
		        transport.open();
		    }
		    TProtocol protocol = new TBinaryProtocol(transport);

		    TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, serverName);
		    ClassLoader cLoader = Thread.currentThread().getContextClassLoader();
	        Class clazz = cLoader.loadClass(ConfConstants.SERVICEAPILIST.get(serverName) + "$Client");
	        Constructor constructor = clazz.getDeclaredConstructor(new Class[] { TProtocol.class });
	         client = constructor.newInstance(new Object[] { multiplexedProtocol });
		} catch (TTransportException e) {
			e.printStackTrace();
			
		}catch (Exception ex) {
		      ex.printStackTrace();
	    }
		return client;
	}
	
	public static void main(String[] args) throws TTransportException {
	    TSocket transport = new TSocket("127.0.0.1", 7911);
	    System.out.println(transport.isOpen());
	    transport.open();
	    System.out.println(transport.isOpen());
	    transport = new TSocket("127.0.0.1", 7911);
        System.out.println(transport.isOpen());
    }
}
