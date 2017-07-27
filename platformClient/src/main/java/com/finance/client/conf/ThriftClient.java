/*
 * Copyright (C), 2013-2017, 上汽集团
 * FileName: ThriftClient.java
 * Author:   pengxiaofei
 * Date:     2017年7月25日 下午2:11:45
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.finance.client.conf;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author pengxiaofei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ThriftClient {
    

    private TTransport transport;
    private Map<String, Object> serviceMap = new HashMap();

    public void init(String ipAddress,int port)
    {
      try
      {
        
        this.transport = new TSocket(ipAddress, port);
        TProtocol protocol = new TBinaryProtocol(this.transport);

        Map serviceNameList = ConfConstants.SERVICENAMELIST;
        Set entrySet = serviceNameList.entrySet();
        Iterator it = entrySet.iterator();
        while (it.hasNext()) {
          Map.Entry m = (Map.Entry)it.next();
          TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, (String)m.getValue());
          ClassLoader cLoader = Thread.currentThread().getContextClassLoader();
          Class clazz = cLoader.loadClass((String)m.getKey() + "$Client");
          Constructor constructor = clazz.getDeclaredConstructor(new Class[] { TProtocol.class });
          Object client = constructor.newInstance(new Object[] { multiplexedProtocol });
          this.serviceMap.put((String)m.getValue(), client);
        }
        
      }
      catch (Exception ex) {
        System.out.println("System error:" + ex.getMessage());
      }
    }

    public void close()
    {
      if (this.transport != null)
        this.transport.close();
    }

    public boolean isOpen()
    {
      if (this.transport != null) {
        return this.transport.isOpen();
      }
      return false;
    }

    public Object getClient(String serviceName)
    {
      return this.serviceMap.get(serviceName);
    }

}
