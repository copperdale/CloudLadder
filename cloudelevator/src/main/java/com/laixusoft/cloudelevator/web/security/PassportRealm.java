package com.laixusoft.cloudelevator.web.security;


import com.laixusoft.cloudelevator.biz.ao.UserAO;
import com.laixusoft.cloudelevator.biz.dal.domain.UserDO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import wint.help.biz.result.Result;

/**
 * Created by apple on 14-6-15 下午12:55.
 */
public class PassportRealm extends AuthorizingRealm {

    private UserAO userAO;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String principal = (String) token.getPrincipal();

        Result result = userAO.viewUserByName(principal);

        if (!result.isSuccess()) {
            throw new UnknownAccountException();//没找到帐号
        }

        UserDO userDO = (UserDO) result.getModels().get("user");

        if (userDO == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        String credentialsSalt = userDO.getCredentialsSalt();

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userDO, //用户
                userDO.getPassword(), //密码
                ByteSource.Util.bytes(credentialsSalt),//salt=username+salt
                getName()  //realm name
        );

        return authenticationInfo;
    }

    public void setUserAO(UserAO userAO) {
        this.userAO = userAO;
    }
}
