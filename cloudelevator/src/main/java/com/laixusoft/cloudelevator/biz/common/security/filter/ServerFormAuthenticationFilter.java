package com.laixusoft.cloudelevator.biz.common.security.filter;

import com.laixusoft.cloudelevator.biz.common.Constants;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by apple on 14-6-14 下午10:25.
 */
public class ServerFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        String fallbackUrl = getSuccessUrl();
        WebUtils.redirectToSavedRequest(request, response, fallbackUrl);
    }

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        if (ae instanceof LockedAccountException) {
            request.setAttribute(Constants.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "该用户已被冻结!");
        } else {
            request.setAttribute(Constants.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "用户名或密码不正确!");
        }
    }

}