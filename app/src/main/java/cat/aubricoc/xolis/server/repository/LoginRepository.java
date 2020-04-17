package cat.aubricoc.xolis.server.repository;

import cat.aubricoc.xolis.core.utils.Callback;
import cat.aubricoc.xolis.server.model.User;
import cat.aubricoc.xolis.server.model.UserAuthentication;
import cat.aubricoc.xolis.server.utils.HttpErrorHandler;
import cat.aubricoc.xolis.server.utils.RequestBuilder;

public class LoginRepository {

    private static final LoginRepository INSTANCE = new LoginRepository();
    private static final String RESOURCE = "/login";

    private LoginRepository() {
        super();
    }

    public static LoginRepository getInstance() {
        return INSTANCE;
    }

    public void add(User user, Callback<UserAuthentication> callback, HttpErrorHandler errorHandler) {
        RequestBuilder.newPostRequest(RESOURCE, UserAuthentication.class)
                .body(user)
                .callback(callback::execute)
                .errorHandler(errorHandler)
                .execute();
    }
}
