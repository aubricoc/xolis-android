package cat.aubricoc.xolis.core.service;

import cat.aubricoc.xolis.Xolis;
import cat.aubricoc.xolis.core.enums.LoginResult;
import cat.aubricoc.xolis.core.enums.RegisterResult;
import cat.aubricoc.xolis.core.utils.Callback;
import cat.aubricoc.xolis.core.utils.Preferences;
import cat.aubricoc.xolis.server.model.User;
import cat.aubricoc.xolis.server.model.UserAuthentication;
import cat.aubricoc.xolis.server.repository.LoginRepository;
import cat.aubricoc.xolis.server.repository.OauthAccessTokenRepository;
import cat.aubricoc.xolis.server.repository.UsersRepository;
import cat.aubricoc.xolis.server.utils.HttpErrorHandler;

public class UserService {

    private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    private static final UserService INSTANCE = new UserService();

    private UserService() {
        super();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public void register(String username, String email, String password, Callback<RegisterResult> callback) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        UsersRepository.getInstance().add(user,
                v -> login(user, loginResult -> callback.execute(LoginResult.OK == loginResult ? RegisterResult.OK : RegisterResult.LOGIN_FAILED)),
                new HttpErrorHandler(409, error -> callback.execute(RegisterResult.getByConflictField(error.getField()))));
    }

    public void login(String username, String password, Callback<LoginResult> callback) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        login(user, callback);
    }

    private void login(User user, Callback<LoginResult> callback) {
        LoginRepository.getInstance().add(user, response -> {
            storeAuthentication(response);
            callback.execute(LoginResult.OK);
        }, new HttpErrorHandler(401, error -> {
            clearAuthentication();
            callback.execute(LoginResult.getByErrorMessage(error.getMessage()));
        }));
    }

    public UserAuthentication getAuthenticatedUser() {
        String username = Xolis.getPreferences().getString(Preferences.AUTH_USERNAME);
        if (username == null) {
            return null;
        }
        UserAuthentication user = new UserAuthentication();
        user.setUsername(username);
        user.setAccessToken(Xolis.getPreferences().getString(Preferences.ACCESS_TOKEN));
        user.setRefreshToken(Xolis.getPreferences().getString(Preferences.REFRESH_TOKEN));
        return user;
    }

    private void storeAuthentication(UserAuthentication userAuthentication) {
        Xolis.getPreferences().store(Preferences.ACCESS_TOKEN, userAuthentication.getAccessToken());
        Xolis.getPreferences().store(Preferences.REFRESH_TOKEN, userAuthentication.getRefreshToken());
        Xolis.getPreferences().store(Preferences.AUTH_USERNAME, userAuthentication.getUsername());
    }

    public void clearAuthentication() {
        Xolis.getPreferences().clear(Preferences.ACCESS_TOKEN);
        Xolis.getPreferences().clear(Preferences.REFRESH_TOKEN);
        Xolis.getPreferences().clear(Preferences.AUTH_USERNAME);
    }

    public void refreshAuthentication(String refreshToken, Callback<Boolean> callback) {
        UserAuthentication request = new UserAuthentication();
        request.setRefreshToken(refreshToken);
        request.setGrantType(GRANT_TYPE_REFRESH_TOKEN);
        OauthAccessTokenRepository.getInstance().add(request, response -> {
            storeAuthentication(response);
            callback.execute(true);
        }, new HttpErrorHandler(401, error -> callback.execute(false)));
    }
}
