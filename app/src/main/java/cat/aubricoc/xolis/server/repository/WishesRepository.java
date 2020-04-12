package cat.aubricoc.xolis.server.repository;

import cat.aubricoc.xolis.server.model.Wish;

public class WishesRepository extends Repository<Wish> {

    private static final WishesRepository INSTANCE = new WishesRepository();
    private static final String RESOURCE = "/wishes";

    private WishesRepository() {
        super(Wish.class, RESOURCE);
    }

    public static WishesRepository getInstance() {
        return INSTANCE;
    }
}
