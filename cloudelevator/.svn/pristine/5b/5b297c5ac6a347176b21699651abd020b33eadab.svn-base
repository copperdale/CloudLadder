package com.laixusoft.cloudelevator.biz.ao.impl;

import com.laixusoft.cloudelevator.biz.ao.UserAO;
import com.laixusoft.cloudelevator.biz.common.security.PasswordHash;
import com.laixusoft.cloudelevator.biz.dal.dao.UserDAO;
import com.laixusoft.cloudelevator.biz.dal.domain.UserDO;
import com.laixusoft.cloudelevator.biz.dto.UserDto;
import com.laixusoft.cloudelevator.biz.query.UserQuery;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wint.help.biz.result.Result;
import wint.help.biz.result.ResultSupport;
import wint.help.biz.result.StringResultCode;
import wint.lang.utils.StringUtil;

import java.util.List;


/**
 * this file is auto generate.
 */
public class UserAOImpl implements UserAO {

    private static final Logger log = LoggerFactory.getLogger(UserAOImpl.class);

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private UserDAO userDAO;

    private PasswordHash passwordHash;

    @Override
    public Result updateUser(UserDO userDO) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            int id = userDO.getId();
            UserDO userIndb = userDAO.queryById(id);
            if (userIndb == null) {
                // here replace your result code like:
                // result.setResultCode(UserDOResultCodes.USER_NOT_EXIST);
                result.setResultCode(new StringResultCode("user not exist"));
                return result;
            }
            userIndb.setUsername(userDO.getUsername());
            userIndb.setPassword(userDO.getPassword());
            userIndb.setSalt(userDO.getSalt());

            userDAO.update(userIndb);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("createUser error", e);
        }
        return result;
    }

    @Override
    public Result saveUser(UserDto dto) {
        Result result = new ResultSupport(false);
        try {
            if(StringUtil.isEmpty(dto.getUsername())){
                result.setResultCode(new StringResultCode("用户名称不能为空"));
                return result;
            }

            if(StringUtil.isEmpty(dto.getPassword())){
                result.setResultCode(new StringResultCode("用户密码不能为空"));
                return result;
            }

            UserDO userDO=new UserDO();

            userDO.setUsername(dto.getUsername());
            userDO.setPassword(dto.getPassword());
            userDO.setSalt(randomNumberGenerator.nextBytes().toHex());
            userDO.setPassword(passwordHash.encryptPassword(userDO.getUsername(), userDO.getPassword(), userDO.getSalt()));

            int id = userDAO.create(userDO);
            result.getModels().put("id", id);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("saveUser error", e);
        }
        return result;
    }

    @Override
    public Result viewUser(int id) {
        Result result = new ResultSupport(false);
        try {
            // TODO add your biz code here

            UserDO userDO = userDAO.queryById(id);
            if (userDO == null) {
                // here replace your result code like:
                // result.setResultCode(UserDOResultCodes.USER_NOT_EXIST);
                result.setResultCode(new StringResultCode("user not exist"));
                return result;
            }

            result.getModels().put("user", userDO);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewUser error", e);
        }
        return result;
    }

    @Override
    public Result viewUserForEdit(int id) {
        Result result = new ResultSupport(false);
        try {
            UserDO userDO = userDAO.queryById(id);
            result.getModels().put("user", userDO);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewUserForEdit error", e);
        }
        return result;
    }

    @Override
    public Result viewList(UserQuery query) {
        Result result = new ResultSupport(false);
        try {
            List<UserDO> users = userDAO.queryForPage(query);

            result.getModels().put("users", users);
            result.getModels().put("query", query);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewList error", e);
        }
        return result;
    }

    @Override
    public Result deleteUser(int id) {
        Result result = new ResultSupport(false);
        try {
            userDAO.delete(id);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("deleteUser error", e);
        }
        return result;
    }

    @Override
    public Result viewUserByName(String name) {
        Result result = new ResultSupport(false);
        try {
            UserDO userDO = userDAO.queryByName(name);
            result.getModels().put("user", userDO);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("viewUserByName error", e);
        }
        return result;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setPasswordHash(PasswordHash passwordHash) {
        this.passwordHash = passwordHash;
    }
}