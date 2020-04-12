package cat.aubricoc.xolis.core.service;

import cat.aubricoc.xolis.Xolis;
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

    public void register(String username, String password, Callback<RegisterResult> callback) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        UsersRepository.getInstance().add(user,
                v -> login(user, logged -> callback.execute(Boolean.TRUE.equals(logged) ? RegisterResult.OK : RegisterResult.LOGIN_FAILED)),
                new HttpErrorHandler(409, v -> callback.execute(RegisterResult.USERNAME_CONFLICT)));
    }

    public void login(String username, String password, Callback<Boolean> callback) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        login(user, callback);
    }

    private void login(User user, Callback<Boolean> callback) {
        LoginRepository.getInstance().add(user, response -> {
            storeToken(response.getAccessToken());
            callback.execute(true);
        }, v -> {
            storeToken(null);
            callback.execute(false);
        });
    }

    private void storeToken(String accessToken) {
        Xolis.getPreferences().store(Preferences.ACCESS_TOKEN, accessToken);
    }
}
