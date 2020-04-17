package cat.aubricoc.xolis.server.utils;

import android.util.Log;
import cat.aubricoc.xolis.Xolis;
import cat.aubricoc.xolis.server.model.HttpError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HttpErrorListener<T> implements Response.ErrorListener {

    private final RequestBuilder<T> request;
    private final Gson gson;
    private final List<HttpErrorHandler> handlers;

    public HttpErrorListener(RequestBuilder<T> request, Gson gson) {
        this.request = request;
        this.gson = gson;
        this.handlers = new ArrayList<>();
    }

    public void addHandlers(List<HttpErrorHandler> handlers) {
        this.handlers.addAll(handlers);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        NetworkResponse networkResponse = error.networkResponse;
        Log.i(Xolis.TAG, "Call to " + request.getEndpoint() + "...KO");
        if (networkResponse == null) {
            throw new IllegalStateException("Unknown error on request", error);
        }
        int statusCode = networkResponse.statusCode;
        Log.e(Xolis.TAG, "Request failed (" + statusCode + "): " + error.getMessage());
        for (HttpErrorHandler handler : handlers) {
            if (handler.canHandle(statusCode)) {
                handler.execute(buildHttpError(networkResponse.data));
                return;
            }
        }
        throw new IllegalStateException("Unknown status code " + statusCode);
    }

    private HttpError buildHttpError(byte[] responseBody) {
        HttpError error;
        if (responseBody == null || responseBody.length == 0) {
            error = new HttpError();
        } else {
            error = gson.fromJson(new String(responseBody), HttpError.class);
        }
        error.setRetry(v -> request.execute());
        return error;
    }
}
