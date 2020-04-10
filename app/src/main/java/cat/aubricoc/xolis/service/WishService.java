package cat.aubricoc.xolis.service;

import cat.aubricoc.xolis.ui.wishes.Wish;
import cat.aubricoc.xolis.ui.wishes.WishClient;
import cat.aubricoc.xolis.utils.Callback;

import java.util.List;

public class WishService {

    private static final WishService INSTANCE = new WishService();

    private WishService() {
        super();
    }

    public static WishService getInstance() {
        return INSTANCE;
    }

    public void saveNew(String name, Callback<Void> callback) {
        Wish wish = new Wish();
        wish.setName(name);
        WishClient.getInstance().saveWish(wish, callback);
    }

    public void getWishes(Callback<List<Wish>> callback) {
        WishClient.getInstance().getWishes(callback);
    }
}
