/*
 * Copyright (C), 2013-2016, 上汽集团
 * FileName: UserEntity.java
 * Author:   dongjiangwei
 * Date:     2016年10月24日 上午11:21:38
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.finance.server.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;




/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 * 用户表
 * @author dongjiangwei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Document(collection="biz.user")
public class BizUserEntity implements Serializable {

    @Id
    private ObjectId id;
    private String userName;  //用户名
    private String passWord;  //密码
    private String salt;  //密码盐值
    private Boolean enabled;  //是否可登陆
    private ObjectId orgId;  //所属机构ID
    private String realName;  //用户姓名
    private String userCode;  //用户代码
    private String userMobile;  //用户手机号
    private String userMail;  //用户邮箱
    private ObjectId cityId;//分管城市Id
    private String cityName; //分管城市名称
    private String loginType; //登录方式 
    private String createUser;  //创建人
    private Long createTime;  //创建时间
    private String updateUser;  //更新人
    private Long updateTime;  //更新时间
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public ObjectId getOrgId() {
        return orgId;
    }
    public void setOrgId(ObjectId orgId) {
        this.orgId = orgId;
    }

    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getUserCode() {
        return userCode;
    }
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    public String getUserMobile() {
        return userMobile;
    }
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
    public String getUserMail() {
        return userMail;
    }
    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
    
  
    public String getCreateUser() {
        return createUser;
    }
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public String getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
    public Long getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    public ObjectId getCityId() {
        return cityId;
    }
    public void setCityId(ObjectId cityId) {
        this.cityId = cityId;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getLoginType() {
        return loginType;
    }
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
    
}
