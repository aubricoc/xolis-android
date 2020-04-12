package cat.aubricoc.xolis.server.utils;

import cat.aubricoc.xolis.core.utils.Callback;

public class HttpErrorHandler {

    private final int statusCode;
    private final Callback<Void> callback;

    public HttpErrorHandler(int statusCode, Callback<Void> callback) {
        this.statusCode = statusCode;
        this.callback = callback;
    }

    public boolean canHandle(int statusCode) {
        return statusCode == this.statusCode;
    }

    public void execute() {
        callback.execute(null);
    }
}
