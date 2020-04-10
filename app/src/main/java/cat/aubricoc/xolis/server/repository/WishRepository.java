package cat.aubricoc.xolis.server.repository;

import android.util.Log;
import cat.aubricoc.xolis.XolisApplication;
import cat.aubricoc.xolis.server.model.Wish;
import cat.aubricoc.xolis.server.utils.Callback;
import cat.aubricoc.xolis.server.utils.RequestBuilder;

import java.util.List;

public class WishRepository {

    private static final WishRepository INSTANCE = new WishRepository();
    public static final String RESOURCE = "/wishes";

    private WishRepository() {
        super();
    }

    public static WishRepository getInstance() {
        return INSTANCE;
    }

    public void add(Wish wish, Callback<Void> callback) {
        Log.i(XolisApplication.TAG, "Call to save a wish...");
        RequestBuilder.newPostRequest(RESOURCE).body(wish).callback(callback::execute).execute();
    }

    public void find(Callback<List<Wish>> callback) {
        Log.i(XolisApplication.TAG, "Call to get wishes...");
        RequestBuilder.newGetListRequest(RESOURCE, Wish.class).callback(callback::execute).execute();
    }
}
