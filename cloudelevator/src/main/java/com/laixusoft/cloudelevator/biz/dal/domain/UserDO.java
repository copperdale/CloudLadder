package com.laixusoft.cloudelevator.biz.dal.domain;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/5/19
 * Time: 下午12:38
 */
public class UserDO extends BaseDO {

    private String username;//用户名称
    private String password;//用户密码
    private String salt; //加密密码的盐

    public String getCredentialsSalt(){
        return username+salt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
