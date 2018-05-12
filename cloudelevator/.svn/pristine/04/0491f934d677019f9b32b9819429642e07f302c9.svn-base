package com.laixusoft.cloudelevator.biz.common.gen;

import wint.help.tools.ibatis.AutoGenDAO;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/4/22
 * Time: 下午12:54
 */
public class LaixuAutoGen {

    private AutoGenDAO autoGenDAO;

    private LaixuAutoGenView autoGenView;

    public LaixuAutoGen(String prefix) {
        autoGenDAO = new AutoGenDAO(prefix);
        autoGenView = new LaixuAutoGenView(prefix);
    }

    public void gen(Class<?> clazz, String idName) {
        autoGenDAO.gen(clazz, idName);
        autoGenView.gen(clazz, idName);
    }

    public void forceGen(Class<?> clazz, String idName) {
        autoGenDAO.forceGen(clazz, idName);
        autoGenView.forceGen(clazz, idName);
    }

    public void gen(Class<?> clazz) {
        autoGenDAO.gen(clazz);
        autoGenView.gen(clazz);
    }

    public void forceGen(Class<?> clazz) {
        autoGenDAO.forceGen(clazz);
        autoGenView.forceGen(clazz);
    }
}
