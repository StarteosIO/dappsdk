package io.starteos.dappsdk.standard;

import io.starteos.dappsdk.Request;

public interface IDAppApiEOS {

    /**
     * 获取当前账户名
     *
     * @param request
     */
    void getCurrentWalletAccount(Request request);

    /**
     * 获取当前钱包余额
     *
     * @param request
     */
    void getCurrentBalance(Request request);

    /**
     * 获取当前账户信息
     *
     * @param request
     */
    void getCurrentAccountInfo(Request request);

    /**
     * 获取账户列表
     *
     * @param request
     */
    void getWalletAccounts(Request request);

    /**
     * 获取余额
     *
     * @param request
     */
    void getBalance(Request request);

    /**
     * 获取账户信息
     *
     * @param request
     */
    void getAccountInfo(Request request);

    /**
     * 转账
     *
     * @param request
     */
    void transfer(Request request);

    /**
     * 获取交易记录
     *
     * @param request
     */
    void getTransactionRecord(Request request);

}
