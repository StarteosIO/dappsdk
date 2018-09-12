package io.starteos.dappsdk.standard;

import io.starteos.dappsdk.Request;

public interface IDAppApiGUI {

    /**
     * 页面跳转
     *
     * @param request
     */
    void jumpNativePage(Request request);

    /**
     * 全屏显示切换
     *
     * @param request
     */
    void changeFullScreen(Request request);

    /**
     * 更改状态栏显示
     *
     * @param request
     */
    void changeStatusBar(Request request);

    /**
     * 显示一个Toast
     *
     * @param request
     */
    void showToast(Request request);

    /**
     * 显示一个确认弹窗
     *
     * @param request
     */
    void showAlert(Request request);

    /**
     * 显示一个对话框
     *
     * @param request
     */
    void showDialog(Request request);

    /**
     * 复制到剪贴板
     *
     * @param request
     */
    void setClipboard(Request request);

    /**
     * 扫描二维码
     *
     * @param request
     */
    void scanQRCode(Request request);

}
