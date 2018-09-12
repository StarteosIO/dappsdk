package io.starteos.dappsdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DAppBridge {

    private static final String API_NAME = "dappApi";
    private static final String NAMESPACE_BUILDER_CLASS = "io.starteos.dappsdk.proxy.Builder";
    private static final String NAMESPACE_BUILDER_METHOD = "getNamespaces";
    private Context context;
    private WebView webView;
    Map<String, List<DAppApi>> namespaces = new HashMap<>();

    public DAppBridge(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
        invokeNamespace(0);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new JsToAndroid(), API_NAME);
    }

    public Context getContext() {
        return context;
    }

    public void destroy() {
        for (List<DAppApi> list : namespaces.values()) {
            for (DAppApi api : list) {
                api.destroy();
            }
        }
    }

    /**
     * 回调到JavaScript的方法
     *
     * @param request
     * @param response
     */
    public void callback(final Request request, final Response response) {
        webView.post(new Runnable() {
            @Override
            public void run() {
                String result = response.toString().replace("\"", "\\\"");
                String jsMethod = String.format("javascript:window.%s(\"%s\")", request.getCallback(), result);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript(jsMethod, null);
                } else {
                    webView.loadUrl(jsMethod);
                }
            }
        });
    }

    /**
     * JavaScript到原生的方法
     */
    class JsToAndroid {
        @JavascriptInterface
        public void request(String request) {
            try {
                Request requestObj = new Request(request);
                if (!namespaces.containsKey(requestObj.getNamespace())) {
                    callback(requestObj, new Response(Response.CODE_ERROR_NAMESPACE, "no such namespace"));
                    return;
                }
                List<DAppApi> list = namespaces.get(requestObj.getNamespace());
                boolean invoked = false;
                for (DAppApi api : list) {
                    if (api.invoke(requestObj)) {
                        invoked = true;
                        break;
                    }
                }
                if (!invoked) {
                    callback(requestObj, new Response(Response.CODE_ERROR_FUNCTION, "no such function"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 递归遍历namespace对象
     *
     * @param index
     */
    private void invokeNamespace(int index) {
        String className = NAMESPACE_BUILDER_CLASS + index;
        try {
            Class clazz = Class.forName(className);
            Method method = clazz.getMethod(NAMESPACE_BUILDER_METHOD);
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            Object object = constructor.newInstance();
            String jsonString = (String) method.invoke(object);
            JSONObject jsonObject = new JSONObject(jsonString);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONArray array = jsonObject.optJSONArray(key);
                List<DAppApi> list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    Class clz = Class.forName(array.optString(i));
                    Method[] methods = clz.getMethods();
                    Constructor<?> cons = clz.getDeclaredConstructor(DAppBridge.class);
                    DAppApi api = (DAppApi) cons.newInstance(this);
                    list.add(api);
                }
                namespaces.put(key, list);
            }
            invokeNamespace(index += 1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSdkVersion() {
        return "1.0.5";
    }

}
