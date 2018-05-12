package com.laixusoft.cloudelevator.biz.common.gen;

import wint.help.tools.gen.common.BaseAutoGen;
import wint.help.tools.gen.common.FileWriter;
import wint.help.tools.gen.common.SourceGenerator;
import wint.help.tools.gen.dao.DaoGenUtil;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/4/22
 * Time: 下午12:55
 */
public class LaixuAutoGenView extends BaseAutoGen {

    private String prefix;

    private String aoPath = "src/main/resources/beans/biz-ao.xml";

    public LaixuAutoGenView(String prefix) {
        this.prefix = prefix;
    }


    private void genImpl(Class<?> clazz, String idName, boolean force) {
        try {
            File baseFile = getProjectBasePath(clazz);

            SourceGenerator sourceGenerator = new SourceGenerator();
            sourceGenerator.setIdName(idName);
            sourceGenerator.setTablePrefix(prefix);

            File javaMainSrcPath = new File(baseFile, JAVA_MAIN_SRC);

            genJavaAO(sourceGenerator, clazz, getFileWriter(force), javaMainSrcPath);
            genJavaAOImpl(sourceGenerator, clazz, baseFile, getFileWriter(force), javaMainSrcPath);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void gen(Class<?> clazz, String idName) {
        genImpl(clazz, idName, false);
    }

    public void gen(Class<?> clazz) {
        gen(clazz, "id");
    }

    public void forceGen(Class<?> clazz, String idName) {
        genImpl(clazz, idName, true);
    }

    public void forceGen(Class<?> clazz) {
        forceGen(clazz, "id");
    }

    private void genJavaAO(SourceGenerator sourceGenerator, Class<?> clazz, FileWriter fileWriter, File javaMainSrcPath) {
        StringWriter stringWriter = new StringWriter();
        String aoFullname = sourceGenerator.genJavaAO(clazz, stringWriter);
        String content = stringWriter.toString();
        this.genJavaSrc(aoFullname, content, javaMainSrcPath, fileWriter);
    }


    private void genJavaAOImpl(SourceGenerator sourceGenerator, Class<?> clazz, File baseFile, FileWriter fileWriter, File javaMainSrcPath) throws IOException {
        StringWriter stringWriter = new StringWriter();
        String aoImplFullname = sourceGenerator.genJavaAOImpl(clazz, stringWriter);
        String content = stringWriter.toString();
        this.genJavaSrc(aoImplFullname, content, javaMainSrcPath, fileWriter);

        File daoSpring = new File(baseFile, aoPath);
        if (daoSpring.exists()) {
            String alias = DaoGenUtil.getDoAlias(clazz);
            addSpringBean(daoSpring, aoImplFullname, alias + "AO");
        }

    }


}
