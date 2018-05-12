package com.laixusoft.cloudelevator.biz.common.security;

import com.laixusoft.cloudelevator.biz.common.utils.StringUtil;
import org.apache.shiro.config.Ini;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by apple on 14-6-20 下午4:18.
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean {

    public static final String PERMISSION_STRING = "perms[\"{0}\"]";

    //shiro默认的链接定义
    private String filterChainDefinitions;

    /**
     * 通过filterChainDefinitions对默认的链接过滤定义
     *
     * @param filterChainDefinitions 默认的接过滤定义
     */
    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }


    @Override
    public Ini.Section getObject() throws BeansException {
        Ini ini = new Ini();
        //加载默认的url
        if (!StringUtil.isEmpty(filterChainDefinitions)) {
            ini.load(filterChainDefinitions);
        }
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        return section;
    }

    @Override
    public Class<?> getObjectType() {
        return Ini.Section.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
