package io.starteos.dappsdk;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 返回到JavaScript的数据
 */
public class Response {
    /**
     * 成功
     */
    public static final int CODE_SUCCESS = 10000;
    /**
     * namespace错误
     */
    public static final int CODE_ERROR_NAMESPACE = -10001;
    /**
     * 方法名错误
     */
    public static final int CODE_ERROR_FUNCTION = -10002;
    /**
     * 参数错误
     */
    public static final int CODE_ERROR_PARAMS = -10003;
    /**
     * 用户取消了操作
     */
    public static final int CODE_ERROR_CANCEL = -10004;

    private int code;
    private String message;
    private Object data;
    private JSONObject dataJson;

    /**
     * 初始化对象后，使用putData添加返回值
     *
     * @param code
     * @param message
     */
    public Response(int code, String message) {
        this.code = code;
        this.message = message;
        this.dataJson = new JSONObject();
    }

    /**
     * 传入自定义的返回数据，但是不能够再使用putData方法
     *
     * @param code
     * @param message
     * @param data
     */
    public Response(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public synchronized void putData(String key, Object value) {
        if (data != null) {
            throw new IllegalStateException("must be use only one of Object's data or JSONObject's data");
        }
        try {
            dataJson.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        JSONObject result = new JSONObject();
        try {
            result.put("code", code);
            result.put("message", message);
            if (data != null) {
                result.put("data", data);
            } else {
                result.put("data", dataJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
