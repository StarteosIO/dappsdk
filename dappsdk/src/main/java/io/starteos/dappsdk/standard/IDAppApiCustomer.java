package io.starteos.dappsdk.standard;

import io.starteos.dappsdk.Request;

public interface IDAppApiCustomer {
    /**
     * 获取当前钱包类型
     *
     * @param request
     */
    void getCurrentWalletType(Request request);

    /**
     * 获取当前账户名
     *
     * @param request
     */
    void getCurrentWalletAccount(Request request);
}
