package cat.aubricoc.xolis.ui.wishes;

import android.util.Log;
import cat.aubricoc.xolis.XolisApp;
import cat.aubricoc.xolis.utils.Callback;
import cat.aubricoc.xolis.utils.network.RequestBuilder;

import java.util.List;

public class WishClient {

    private static final WishClient INSTANCE = new WishClient();
    public static final String RESOURCE = "/wishes";

    private WishClient() {
        super();
    }

    public static WishClient getInstance() {
        return INSTANCE;
    }

    public void getWishes(Callback<List<Wish>> callback) {
        Log.i(XolisApp.TAG, "Call to get wishes...");
        RequestBuilder.newGetListRequest(RESOURCE, Wish.class).callback(callback::execute).execute();
    }

    public void saveWish(Wish wish, Callback<Void> callback) {
        Log.i(XolisApp.TAG, "Call to save a wish...");
        RequestBuilder.newPostRequest(RESOURCE).body(wish).callback(callback::execute).execute();
    }
}
