package com.laixusoft.cloudelevator.web.common.base;

import wint.help.biz.result.Result;
import wint.help.biz.result.ResultCode;
import wint.help.biz.result.results.CommonResultCodes;
import wint.help.mvc.security.csrf.CsrfTokenUtil;
import wint.lang.utils.UrlUtil;
import wint.mvc.flow.FlowData;
import wint.mvc.holder.WintContext;
import wint.mvc.template.Context;
import wint.mvc.view.types.ViewTypes;

import java.util.Map;

public class BaseAction {

    public static final String JSON_TYPE = "application/json;charset=UTF-8";

    private static final String TOKEN_KEY = "x_token";

    protected void handleError(Result result, FlowData flowData, Context context) {
        if (result.isHasFieldResultCodes()) {
            return;
        }
        ResultCode resultCode = getResultCode(result, flowData);
        if (resultCode == null) {
            resultCode = CommonResultCodes.SYSTEM_ERROR;
        }
        context.put("resultCode", resultCode);
        flowData.setTarget("error");
    }

    private ResultCode getResultCode(Result result, FlowData flowData) {
        if (result.isHasFieldResultCodes()) {
            return null;
        }
        ResultCode resultCode = result.getResultCode();
        /*
        if (UserResultCodes.USER_NOT_LOGIN == resultCode) {
            flowData.redirectTo("userModule", "login");
            return null;
        }
        */
        if (resultCode == null) {
            return CommonResultCodes.SYSTEM_ERROR;
        }
        return resultCode;
    }

    protected Object result2Context(Result result, Context context, String name) {
        Object value = result.getModels().get(name);
        context.put(name, value);
        return value;
    }

    protected void result2Context(Result result, Context context) {
        for (Map.Entry<String, Object> entry : result.getModels().entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }
    }

    protected void proccessResult(Result result, FlowData flowData, Context context) {
        if (result.isSuccess()) {
            this.result2Context(result, context);
        } else {
            this.handleError(result, flowData, context);
        }
    }

    protected void proccessJsonResult(Result result, FlowData flowData, Context context) {
        flowData.setViewType(ViewTypes.JSON_VIEW_TYPE);
        flowData.setContentType(JSON_TYPE);
        result2Context(result, context);
        context.put(TOKEN_KEY, CsrfTokenUtil.token());
        if (result != null) {
            ResultCode resultCode = result.getResultCode();
            if (!result.isSuccess()) {
                if (resultCode == null) {
                    resultCode = CommonResultCodes.SYSTEM_ERROR;
                }
            }
            context.put("status", result.isSuccess());
            if (resultCode != null) {
                context.put("message", resultCode.getMessage());
            }
        } else {
            context.put("status", false);
        }
    }

    protected String getRequestURL() {
        return UrlUtil.getRequestURL(WintContext.getRequest());
    }

}
