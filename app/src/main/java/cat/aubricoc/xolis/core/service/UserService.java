package cat.aubricoc.xolis.core.service;

import cat.aubricoc.xolis.Xolis;
import cat.aubricoc.xolis.core.enums.LoginResult;
import cat.aubricoc.xolis.core.enums.RegisterResult;
import cat.aubricoc.xolis.core.utils.Callback;
import cat.aubricoc.xolis.core.utils.Preferences;
import cat.aubricoc.xolis.server.model.User;
import cat.aubricoc.xolis.server.repository.LoginRepository;
import cat.aubricoc.xolis.server.repository.UsersRepository;
import cat.aubricoc.xolis.server.utils.HttpErrorHandler;

public class UserService {

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
            storeAuthentication(response.getAccessToken(), response.getUsername());
            callback.execute(LoginResult.OK);
        }, new HttpErrorHandler(401, error -> {
            clearAuthentication();
            callback.execute(LoginResult.getByErrorMessage(error.getMessage()));
        }));
    }

    public User getAuthenticatedUser() {
        String username = Xolis.getPreferences().getString(Preferences.AUTH_USERNAME);
        if (username == null) {
            return null;
        }
        User user = new User();
        user.setUsername(username);
        return user;
    }

    private void storeAuthentication(String accessToken, String username) {
        Xolis.getPreferences().store(Preferences.ACCESS_TOKEN, accessToken);
        Xolis.getPreferences().store(Preferences.AUTH_USERNAME, username);
    }

    public void clearAuthentication() {
        Xolis.getPreferences().clear(Preferences.ACCESS_TOKEN);
        Xolis.getPreferences().clear(Preferences.AUTH_USERNAME);
    }
}
