package com.finance.server.conf;

import java.lang.reflect.Constructor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.finance.server.anno.ServiceThriftAnno;

@Configuration
public class ThriftConfig {
    @Autowired
    ApplicationContext context;
    
    @Value("${thrift-port}")
    private int port;
    
    @Value("${thrift.server.stoptimeout}")
    private int stopTimeout;

    @Value("${thrift.server.reqtimeout}")
    private int reqTimeout;
    
    @Autowired
    private ZooKeeperConfig zooKeeperConfig;
    
    private TServerTransport transport = null;
    private TServer server = null;

    ExecutorService executor = Executors.newSingleThreadExecutor();

   
    public TServer tServer() {
        try {
            this.transport = new TServerSocket(this.port);
            TMultiplexedProcessor mProcessor = new TMultiplexedProcessor();
            TProcessor processor = null;
            String[] beanDefinitionNames = context.getBeanDefinitionNames();
            for (String name : beanDefinitionNames) {
                Object bean = context.getBean(name);
                if (!bean.getClass().isAnnotationPresent(ServiceThriftAnno.class)) {
                    continue;
                }

                Class[] infs = bean.getClass().getInterfaces();
                String pName = null;
                for (Class inf : infs) {
                    pName = inf.getEnclosingClass().getName();

                    ClassLoader cLoader = bean.getClass().getClassLoader();
                    Class pclass = cLoader.loadClass(pName + "$Processor");
                    if (!TProcessor.class.isAssignableFrom(pclass)) {
                        continue;
                    }
                    String serviceName = ((ServiceThriftAnno) bean.getClass().getAnnotation(ServiceThriftAnno.class)).serviceName();

                    Constructor constructor = pclass.getConstructor(new Class[] { inf });
                    processor = (TProcessor) constructor.newInstance(new Object[] { bean });

                    mProcessor.registerProcessor(serviceName, processor);
                    ConfConstants.REGISTLIST.add(serviceName);
                    System.out.println("Service:{} is registered" + serviceName);
                    break;

                }
                
                TThreadPoolServer.Args args = new TThreadPoolServer.Args(this.transport);
                args.requestTimeout(reqTimeout);
                args.stopTimeoutVal(stopTimeout);
                args.processor(mProcessor);

                server = new TThreadPoolServer(args);

            }

        } catch (Exception e) {
           e.printStackTrace();
        }
        zooKeeperConfig.init();   
        return server;
    }

    @PostConstruct
    public void init() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                tServer().serve();
                
                
            }
        });
    }
}
