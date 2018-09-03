package io.starteos.dappsdk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DAppApi {

    protected DAppBridge bridge;

    public DAppApi(DAppBridge bridge) {
        this.bridge = bridge;
    }

    public boolean invoke(Request request) {
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
