package cat.aubricoc.xolis.server.utils;

import android.util.Log;
import cat.aubricoc.xolis.Xolis;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

public class HttpErrorListener implements Response.ErrorListener {

    private final List<HttpErrorHandler> handlers = new ArrayList<>();

    public void addHandlers(List<HttpErrorHandler> handlers) {
        this.handlers.addAll(handlers);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        NetworkResponse networkResponse = error.networkResponse;
        if (networkResponse == null) {
            throw new IllegalStateException("Unknown error on request", error);
        }
        int statusCode = networkResponse.statusCode;
        Log.e(Xolis.TAG, "Request failed (" + statusCode + "): " + error.getMessage());
        for (HttpErrorHandler handler : handlers) {
            if (handler.canHandle(statusCode)) {
                handler.execute();
                return;
            }
        }
        throw new IllegalStateException("Unknown status code " + statusCode);
    }
}
