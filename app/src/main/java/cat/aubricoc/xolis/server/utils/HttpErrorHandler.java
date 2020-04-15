package cat.aubricoc.xolis.server.utils;

import cat.aubricoc.xolis.core.utils.Callback;
import cat.aubricoc.xolis.server.model.HttpError;

public class HttpErrorHandler {

    private final int statusCode;
    private final Callback<HttpError> callback;

    public HttpErrorHandler(int statusCode, Callback<HttpError> callback) {
        this.statusCode = statusCode;
        this.callback = callback;
    }

    public boolean canHandle(int statusCode) {
        return statusCode == this.statusCode;
    }

    public void execute(HttpError httpError) {
        callback.execute(httpError);
    }
}
