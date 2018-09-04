package io.starteos.dappsdk;

import org.json.JSONException;
import org.json.JSONObject;

public class Request {

    private String namespace;
    private String function;
    private String callback;
    private JSONObject params;

    public Request(String json) throws JSONException {
        this(new JSONObject(json));
    }

    public Request(JSONObject json) {
        namespace = json.optString("namespace");
        function = json.optString("function");
        callback = json.optString("callback");
        params = json.optJSONObject("params");
    }

    public String getNamespace() {
        return namespace;
    }

    public String getFunction() {
        return function;
    }

    public String getCallback() {
        return callback;
    }

    public JSONObject getParams() {
        return params;
    }
}