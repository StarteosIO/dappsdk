package io.starteos.dappsdk.standard;

import io.starteos.dappsdk.Request;

public interface IDAppApiCustomer {
    /**
     * 获取当前钱包类型
     *
     * @param request
     */
    void getCurrentWalletType(Request request);

}
