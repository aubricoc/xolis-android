package cat.aubricoc.xolis.core.service;

import cat.aubricoc.xolis.Xolis;
import cat.aubricoc.xolis.core.utils.Callback;
import cat.aubricoc.xolis.core.utils.Preferences;
import cat.aubricoc.xolis.server.model.User;
import cat.aubricoc.xolis.server.repository.LoginRepository;
import cat.aubricoc.xolis.server.repository.UsersRepository;

public class UserService {

    private static final UserService INSTANCE = new UserService();

    private UserService() {
        super();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public void register(String username, String password, Callback<Boolean> callback) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        UsersRepository.getInstance().add(user, v -> login(user, callback));
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
