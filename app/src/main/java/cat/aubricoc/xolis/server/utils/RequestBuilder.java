package cat.aubricoc.xolis.server.utils;

import android.util.Log;
import cat.aubricoc.xolis.XolisApplication;
import cat.aubricoc.xolis.XolisContext;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

public class RequestBuilder<T> {

    public static final Gson GSON = new Gson();
    private static final ErrorListener ERROR_LISTENER = new ErrorListener();

    private final int method;
    private final String url;
    private final Type type;
    private Object body;
    private Response.Listener<T> callback;

    private RequestBuilder(int method, String url, Type type) {
        this.method = method;
        this.url = XolisContext.getServerUrl() + url;
        this.type = type;
    }

    public static <V> RequestBuilder<V> newGetObjectRequest(String urlPath, Class<V> type) {
        return new RequestBuilder<>(Request.Method.GET, urlPath, TypeToken.get(type).getType());
    }

    public static <V> RequestBuilder<List<V>> newGetListRequest(String urlPath, Class<V> type) {
        return new RequestBuilder<>(Request.Method.GET, urlPath, TypeToken.getParameterized(List.class, type).getType());
    }

    public static RequestBuilder<Void> newPostRequest(String urlPath) {
        return new RequestBuilder<>(Request.Method.POST, urlPath, null);
    }

    public RequestBuilder<T> body(Object body) {
        this.body = body;
        return this;
    }

    public RequestBuilder<T> callback(Response.Listener<T> callback) {
        this.callback = callback;
        return this;
    }

    public void execute() {
        GsonRequest request = new GsonRequest(parseBody());
        XolisContext.getRequestQueue().add(request);
    }

    private String parseBody() {
        if (body == null) {
            return null;
        }
        return GSON.toJson(body);
    }

    private static class ErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(XolisApplication.TAG, "Request failed: " + error.getMessage());
        }
    }

    private class GsonRequest extends JsonRequest<T> {

        private GsonRequest(String requestBody) {
            super(method, url, requestBody, callback, ERROR_LISTENER);
        }

        @Override
        protected Response<T> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                T object = GSON.fromJson(jsonString, type);
                return Response.success(object, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException | JsonParseException e) {
                return Response.error(new ParseError(e));
            }
        }
    }
}
