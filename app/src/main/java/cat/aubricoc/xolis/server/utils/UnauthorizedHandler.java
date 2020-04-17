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

        private static final int MAX_ATTEMPTS = 3;

        private int attemptsFailed = 0;

        @Override
        public void execute(HttpError error) {
            attemptsFailed++;
            if (attemptsFailed >= MAX_ATTEMPTS) {
                UserService.getInstance().clearAuthentication();
                Xolis.goToAuthentication();
            }
            UserAuthentication user = UserService.getInstance().getAuthenticatedUser();
            if (user == null) {
                Xolis.goToAuthentication();
            } else {
                UserService.getInstance().refreshAuthentication(user.getRefreshToken(), result -> {
                    if (Boolean.TRUE.equals(result)) {
                        error.getRetry().execute(null);
                    } else {
                        Xolis.goToAuthentication();
                    }
                });
            }
        }
    }
}
