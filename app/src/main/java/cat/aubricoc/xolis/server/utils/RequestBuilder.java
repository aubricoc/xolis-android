package cat.aubricoc.xolis.server.utils;

import android.text.TextUtils;
import android.util.Log;
import cat.aubricoc.xolis.Xolis;
import cat.aubricoc.xolis.core.utils.Preferences;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestBuilder<T> {

    private final Gson gson;
    private final String endpoint;
    private final HttpMethod method;
    private final String url;
    private final Type type;
    private final HttpErrorListener<T> errorListener;
    private final Map<String, String> params;
    private Object body;
    private Response.Listener<T> callback;

    private RequestBuilder(HttpMethod method, String url, Type type) {
        this.gson = new Gson();
        this.endpoint = method + " " + url;
        this.method = method;
        this.url = Xolis.getServerUrl() + url;
        this.type = type;
        this.errorListener = new HttpErrorListener<>(this, gson);
        this.params = new HashMap<>();
    }

    public static <V> RequestBuilder<V> newGetObjectRequest(String urlPath, Class<V> type) {
        return new RequestBuilder<>(HttpMethod.GET, urlPath, TypeToken.get(type).getType());
    }

    public static <V> RequestBuilder<List<V>> newGetListRequest(String urlPath, Class<V> type) {
        return new RequestBuilder<>(HttpMethod.GET, urlPath, TypeToken.getParameterized(List.class, type).getType());
    }

    public static RequestBuilder<Void> newPostRequest(String urlPath) {
        return new RequestBuilder<>(HttpMethod.POST, urlPath, null);
    }

    public static <V> RequestBuilder<V> newPostRequest(String urlPath, Class<V> type) {
        return new RequestBuilder<>(HttpMethod.POST, urlPath, TypeToken.get(type).getType());
    }

    public String getEndpoint() {
        return endpoint;
    }

    public RequestBuilder<T> param(String name, String value) {
        params.put(name, value);
        return this;
    }

    public RequestBuilder<T> body(Object body) {
        this.body = body;
        return this;
    }

    public RequestBuilder<T> callback(Response.Listener<T> callback) {
        this.callback = callback;
        return this;
    }

    public RequestBuilder<T> errorHandler(HttpErrorHandler... handlers) {
        this.errorListener.addHandlers(Arrays.asList(handlers));
        return this;
    }

    public void execute() {
        Log.i(Xolis.TAG, "Call to " + endpoint + "...");
        GsonRequest request = new GsonRequest(prepareUrlWithQueryString(), parseBody());
        Xolis.getRequestQueue().add(request);
    }

    private String prepareUrlWithQueryString() {
        if (params.isEmpty()) {
            return url;
        }
        return url + "?" + prepareQueryString();
    }

    private String prepareQueryString() {
        return TextUtils.join("&", prepareParams(params));
    }

    private List<String> prepareParams(Map<String, String> params) {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String name = encodeUrlParam(entry.getKey());
            String value = encodeUrlParam(entry.getValue());
            list.add(name + "=" + value);
        }
        return list;
    }

    private String encodeUrlParam(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Encoding failed", e);
        }
    }

    private String parseBody() {
        if (body == null) {
            return null;
        }
        return gson.toJson(body);
    }

    private enum HttpMethod {
        GET(Request.Method.GET),
        POST(Request.Method.POST);

        private final int id;

        HttpMethod(int id) {
            this.id = id;
        }
    }

    private class GsonRequest extends JsonRequest<T> {

        private GsonRequest(String urlWithQueryString, String requestBody) {
            super(method.id, urlWithQueryString, requestBody, callback, errorListener);
        }

        @Override
        protected Response<T> parseNetworkResponse(NetworkResponse response) {
            try {
                Log.i(Xolis.TAG, "Call to " + endpoint + "...OK");
                String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                T object = gson.fromJson(jsonString, type);
                return Response.success(object, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException | JsonParseException e) {
                return Response.error(new ParseError(e));
            }
        }

        @Override
        protected void deliverResponse(T response) {
            callback.onResponse(response);
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> originalHeaders = super.getHeaders();
            String token = Xolis.getPreferences().getString(Preferences.ACCESS_TOKEN);
            if (token == null) {
                return originalHeaders;
            }
            HashMap<String, String> headers = new HashMap<>();
            if (originalHeaders != null) {
                headers.putAll(originalHeaders);
            }
            headers.put("Authorization", "Bearer " + token);
            return headers;
        }
    }
}
