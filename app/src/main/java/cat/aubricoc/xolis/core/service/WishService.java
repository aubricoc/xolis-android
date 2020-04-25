package cat.aubricoc.xolis.core.service;

import cat.aubricoc.xolis.core.utils.Callback;
import cat.aubricoc.xolis.server.model.Wish;
import cat.aubricoc.xolis.server.repository.WishesRepository;

import java.util.Date;
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
        WishesRepository.getInstance().add(wish, callback);
    }

    public void getWishes(Callback<List<Wish>> callback) {
        WishesRepository.getInstance().find(callback);
    }

    public void getMoreWishes(Date createdFrom, Callback<List<Wish>> callback) {
        WishesRepository.getInstance().find(createdFrom, callback);
    }
}
