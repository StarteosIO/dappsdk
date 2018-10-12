package io.starteos.dappsdk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class DAppApi {

    protected DAppBridge bridge;

    private boolean isDestroy = false;

    public DAppApi(DAppBridge bridge) {
        this.bridge = bridge;
    }

    void destroy() {
        isDestroy = true;
        onDestroy();
    }

    protected abstract void onDestroy();

    public boolean invoke(Request request) {
        if (isDestroy) {
            return false;
        }
        String methodName = request.getFunction();
        Class clazz = getClass();
        try {
            Method method = clazz.getMethod(methodName, Request.class);
            method.invoke(this, request);
            return true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

}
