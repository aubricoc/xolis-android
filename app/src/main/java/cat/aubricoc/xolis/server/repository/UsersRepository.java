package cat.aubricoc.xolis.server.repository;

import cat.aubricoc.xolis.server.model.User;

public class UsersRepository extends Repository<User> {

    private static final UsersRepository INSTANCE = new UsersRepository();
    private static final String RESOURCE = "/users";

    private UsersRepository() {
        super(User.class, RESOURCE);
    }

    public static UsersRepository getInstance() {
        return INSTANCE;
    }
}
