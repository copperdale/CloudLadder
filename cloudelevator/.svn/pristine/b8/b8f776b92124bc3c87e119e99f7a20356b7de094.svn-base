package com.laixusoft.cloudelevator.biz.common.utils;

import com.laixusoft.cloudelevator.biz.dal.domain.UserDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/4/24
 * Time: 下午11:47
 */
public class SystemVariableUtils {

    /**
     * 获取当前登录信息
     *
     * @return {@link com.laixusoft.cloudelevator.biz.dal.domain.UserDO}
     */
    public static UserDO getSessionVariable() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.getPrincipal() != null && subject.getPrincipal() instanceof UserDO) {
            return (UserDO) subject.getPrincipal();
        }
        return null;
    }

    /**
     * 获取登录名称
     *
     * @return
     */
    public static String getLoginName() {
        UserDO sessionVariable = getSessionVariable();
        if (sessionVariable == null) {
            return "";
        } else {
            return sessionVariable.getUsername();
        }
    }


}
