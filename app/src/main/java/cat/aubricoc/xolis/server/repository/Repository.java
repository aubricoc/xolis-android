package cat.aubricoc.xolis.server.repository;

import android.util.Log;
import cat.aubricoc.xolis.Xolis;
import cat.aubricoc.xolis.core.utils.Callback;
import cat.aubricoc.xolis.server.utils.RequestBuilder;

import java.util.List;

public abstract class Repository<T> {

    private final Class<T> type;
    private final String resourcePath;

    protected Repository(Class<T> type, String resourcePath) {
        this.type = type;
        this.resourcePath = resourcePath;
    }

    public void add(T object, Callback<Void> callback) {
        Log.i(Xolis.TAG, "Call to save a " + type.getSimpleName() + "...");
        RequestBuilder.newPostRequest(resourcePath).body(object).callback(callback::execute).execute();
    }

    public void find(Callback<List<T>> callback) {
        Log.i(Xolis.TAG, "Call to find list of " + type.getSimpleName() + "...");
        RequestBuilder.newGetListRequest(resourcePath, type).callback(callback::execute).execute();
    }
}
