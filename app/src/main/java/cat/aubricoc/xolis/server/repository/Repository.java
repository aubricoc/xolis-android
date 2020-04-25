package cat.aubricoc.xolis.server.repository;

import cat.aubricoc.xolis.core.utils.Callback;
import cat.aubricoc.xolis.server.utils.HttpErrorHandler;
import cat.aubricoc.xolis.server.utils.RequestBuilder;
import cat.aubricoc.xolis.server.utils.UnauthorizedHandler;

import java.util.List;

public abstract class Repository<T> {

    private final Class<T> type;
    private final String resourcePath;

    protected Repository(Class<T> type, String resourcePath) {
        this.type = type;
        this.resourcePath = resourcePath;
    }

    public void add(T object, Callback<Void> callback, HttpErrorHandler... errorHandlers) {
        RequestBuilder.newPostRequest(resourcePath)
                .body(object)
                .callback(callback::execute)
                .errorHandler(new UnauthorizedHandler())
                .errorHandler(errorHandlers)
                .execute();
    }

    public void find(Callback<List<T>> callback, HttpErrorHandler... errorHandlers) {
        prepareFind(callback, errorHandlers).execute();
    }

    protected RequestBuilder<List<T>> prepareFind(Callback<List<T>> callback, HttpErrorHandler[] errorHandlers) {
        return RequestBuilder.newGetListRequest(resourcePath, type)
                .callback(callback::execute)
                .errorHandler(new UnauthorizedHandler())
                .errorHandler(errorHandlers);
    }
}
