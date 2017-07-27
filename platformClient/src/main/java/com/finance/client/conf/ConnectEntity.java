/*
 * Copyright (C), 2013-2017, 上汽集团
 * FileName: ConnectEntity.java
 * Author:   pengxiaofei
 * Date:     2017年7月25日 下午2:24:15
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.finance.client.conf;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author pengxiaofei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ConnectEntity {

    
    private String ipAddress;
    
    private Integer port;
    
    private String serverName;

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * @return the serverName
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * @param serverName the serverName to set
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    
    
    
    
    
}
