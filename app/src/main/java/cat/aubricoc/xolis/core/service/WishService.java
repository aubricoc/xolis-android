package cat.aubricoc.xolis.core.service;

import cat.aubricoc.xolis.server.model.Wish;
import cat.aubricoc.xolis.server.repository.WishRepository;
import cat.aubricoc.xolis.server.utils.Callback;

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
        WishRepository.getInstance().add(wish, callback);
    }

    public void getWishes(Callback<List<Wish>> callback) {
        WishRepository.getInstance().find(callback);
    }
}
