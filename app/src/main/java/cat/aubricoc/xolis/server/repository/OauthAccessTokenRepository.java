package cat.aubricoc.xolis.server.repository;

import cat.aubricoc.xolis.core.utils.Callback;
import cat.aubricoc.xolis.server.model.UserAuthentication;
import cat.aubricoc.xolis.server.utils.HttpErrorHandler;
import cat.aubricoc.xolis.server.utils.RequestBuilder;

public class OauthAccessTokenRepository {

    private static final OauthAccessTokenRepository INSTANCE = new OauthAccessTokenRepository();
    private static final String RESOURCE = "/oauth/access_token";

    private OauthAccessTokenRepository() {
        super();
    }

    public static OauthAccessTokenRepository getInstance() {
        return INSTANCE;
    }

    public void add(UserAuthentication userAuthentication, Callback<UserAuthentication> callback, HttpErrorHandler errorHandler) {
        RequestBuilder.newPostRequest(RESOURCE, UserAuthentication.class)
                .body(userAuthentication)
                .callback(callback::execute)
                .errorHandler(errorHandler)
                .execute();
    }
}
