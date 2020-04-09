package cat.aubricoc.xolis.ui.wishes;

import android.util.Log;
import cat.aubricoc.xolis.XolisApp;
import cat.aubricoc.xolis.utils.Callback;
import cat.aubricoc.xolis.utils.network.RequestBuilder;

import java.util.List;

public class WishClient {

    private static final WishClient INSTANCE = new WishClient();

    private WishClient() {
        super();
    }

    public static WishClient getInstance() {
        return INSTANCE;
    }

    public void getWishes(Callback<List<Wish>> callback) {

        Log.i(XolisApp.TAG, "Call to get wishes...");
        String url = "http://192.168.1.132:8080/wishes";

        RequestBuilder.newGetListRequest(url, Wish.class).callback(callback::execute).execute();
    }
}
