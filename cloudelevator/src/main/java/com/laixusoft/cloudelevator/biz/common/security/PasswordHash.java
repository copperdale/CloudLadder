package com.laixusoft.cloudelevator.biz.common.security;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/4/26
 * Time: 上午9:08
 */
public class PasswordHash {

    private String hashAlgorithmName;

    private int hashIterations;

    public String encryptPassword(String username, String password, String salt) {
        String newPassword = new SimpleHash(
                hashAlgorithmName,
                password,
                ByteSource.Util.bytes(username + salt),
                hashIterations).toHex();
        return newPassword;
    }

    public String getHashAlgorithmName() {
        return hashAlgorithmName;
    }

    public void setHashAlgorithmName(String hashAlgorithmName) {
        this.hashAlgorithmName = hashAlgorithmName;
    }

    public int getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }
}
