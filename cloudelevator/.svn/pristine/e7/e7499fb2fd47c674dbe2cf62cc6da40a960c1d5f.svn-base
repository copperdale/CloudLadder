package com.laixusoft.cloudelevator.biz.common.utils;

import wint.help.tools.WintUtil;
import wint.lang.utils.StringUtil;
import wint.mvc.holder.WintContext;
import wint.sessionx.cookie.WintCookie;

import javax.servlet.http.Cookie;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/5/19
 * Time: 下午9:47
 */
public class CookieUtil {

    public static void setCookie(String key, String value, int maxage) {
        WintCookie cookie = new WintCookie(key, value);
        cookie.setMaxAge(maxage);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        WintUtil.getResponse().addCookie(cookie);
    }

    public static String getCookie(String key) {
        String value = "";
        Cookie[] cookies = WintContext.getRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtil.equals(cookie.getName(), key)) {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }

    public static void removeCookie(String key){
        WintCookie cookie = new WintCookie(key, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        WintUtil.getResponse().addCookie(cookie);
    }
}
