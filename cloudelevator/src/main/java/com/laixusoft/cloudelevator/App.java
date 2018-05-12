package com.laixusoft.cloudelevator;

import com.laixusoft.cloudelevator.biz.common.gen.LaixuAutoGen;
import com.laixusoft.cloudelevator.biz.dal.domain.*;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/5/20
 * Time: 下午1:59
 */
public class App {

    public static void main(String[] args) {
        LaixuAutoGen autoGen = new LaixuAutoGen("");
        autoGen.forceGen(UserDO.class);
        autoGen.forceGen(DeviceDO.class);
        autoGen.forceGen(MediaDO.class);
        autoGen.forceGen(DeviceMediaDO.class);
        autoGen.forceGen(NoticeDO.class);
    }

}
