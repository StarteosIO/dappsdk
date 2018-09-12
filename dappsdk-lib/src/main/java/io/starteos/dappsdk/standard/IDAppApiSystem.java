package io.starteos.dappsdk.standard;

import io.starteos.dappsdk.Request;

public interface IDAppApiSystem {

    /**
     * 获取Native SDK信息，返回DAppBridge中的getSdkVersion即可
     *
     * @param request
     */
    void getSdkInfo(Request request);

    /**
     * 获取当前语言设置
     *
     * @param request
     */
    void getLanguageSetting(Request request);

    /**
     * 获取当前显示币种
     *
     * @param request
     */
    void getSymbol(Request request);

}
