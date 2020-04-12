package cat.aubricoc.xolis.server.repository;

import android.util.Log;
import cat.aubricoc.xolis.Xolis;
import cat.aubricoc.xolis.core.utils.Callback;
import cat.aubricoc.xolis.server.model.LoginSuccess;
import cat.aubricoc.xolis.server.model.User;
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

    public void add(User user, Callback<LoginSuccess> callback, Callback<Void> noLoggedCallback) {
        Log.i(Xolis.TAG, "Call to login...");
        RequestBuilder.newPostRequest(RESOURCE, LoginSuccess.class)
                .body(user)
                .callback(callback::execute)
                .errorHandler(new HttpErrorHandler(401, noLoggedCallback))
                .execute();
    }
}
