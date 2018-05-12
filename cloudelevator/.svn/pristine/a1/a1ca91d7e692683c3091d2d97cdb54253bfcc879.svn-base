package com.laixusoft.cloudelevator.web.common.pipeline.valves;

import wint.lang.utils.TargetUtil;
import wint.mvc.flow.InnerFlowData;
import wint.mvc.pipeline.PipelineContext;
import wint.mvc.pipeline.valves.AbstractValve;

/**
 * 版权所有: 杭州睐旭信息科技有限公司
 * Created by User: apple
 * Date: 15/4/26
 * Time: 上午8:49
 */
public class SetDialogValve extends AbstractValve {

    public void invoke(PipelineContext pipelineContext, InnerFlowData flowData) {
        String target = flowData.getTarget();
        target = TargetUtil.normalizeTarget(target);
        if (target.toLowerCase().endsWith("dialog")) {
            flowData.setLayout("dialog");
        }
        pipelineContext.invokeNext(flowData);
    }
}
