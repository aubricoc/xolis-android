package cat.aubricoc.xolis.server.utils;

import cat.aubricoc.xolis.Xolis;
import cat.aubricoc.xolis.core.service.UserService;
import cat.aubricoc.xolis.core.utils.Callback;
import cat.aubricoc.xolis.server.model.HttpError;
import cat.aubricoc.xolis.server.model.UserAuthentication;

public class UnauthorizedHandler extends HttpErrorHandler {

    public UnauthorizedHandler() {
        super(401, new UnauthorizedCallback());
    }

    private static class UnauthorizedCallback implements Callback<HttpError> {

        @Override
        public void execute(HttpError data) {
            UserAuthentication user = UserService.getInstance().getAuthenticatedUser();
            if (user == null) {
                Xolis.goToAuthentication();
            } else {
                UserService.getInstance().refreshAuthentication(user.getRefreshToken(), result -> {
                    if (Boolean.FALSE.equals(result)) {
                        Xolis.goToAuthentication();
                    }
                });
            }
        }
    }
}
