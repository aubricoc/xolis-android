package cat.aubricoc.xolis.server.utils;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestBuilder<T> {

    public static final Gson GSON = new Gson();

    private final int method;
    private final String url;
    private final Type type;
    private final HttpErrorListener errorListener;
    private Object body;
    private Response.Listener<T> callback;

    private RequestBuilder(int method, String url, Type type) {
        this.method = method;
        this.url = Xolis.getServerUrl() + url;
        this.type = type;
        this.errorListener = new HttpErrorListener();
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

    public static <V> RequestBuilder<V> newPostRequest(String urlPath, Class<V> type) {
        return new RequestBuilder<>(Request.Method.POST, urlPath, TypeToken.get(type).getType());
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
        GsonRequest request = new GsonRequest(parseBody());
        Xolis.getRequestQueue().add(request);
    }

    private String parseBody() {
        if (body == null) {
            return null;
        }
        return GSON.toJson(body);
    }

    private class GsonRequest extends JsonRequest<T> {

        private GsonRequest(String requestBody) {
            super(method, url, requestBody, callback, errorListener);
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
