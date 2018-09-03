package io.starteos.dappsdk.standard;

import io.starteos.dappsdk.Request;

public interface IDAppApiEOS {

    /**
     * 获取当前钱包余额
     *
     * @param request
     */
    void getCurrentBalance(Request request);

    /**
     * 转账
     *
     * @param request
     */
    void transfer(Request request);

    /**
     * 获取账户信息
     *
     * @param request
     */
    void getAccountInfo(Request request);

    /**
     * 获取交易记录
     *
     * @param request
     */
    void getTransactionRecord(Request request);

}
