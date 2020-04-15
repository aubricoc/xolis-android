package cat.aubricoc.xolis.core.enums;

public enum LoginResult {

    OK(null),
    USER_NOT_FOUND("User Not Found"),
    PASSWORD_INCORRECT("Credentials Do Not Match");

    private final String errorMesage;

    LoginResult(String errorMesage) {
        this.errorMesage = errorMesage;
    }

    public static LoginResult getByErrorMessage(String message) {
        for (LoginResult result : values()) {
            if (message.equals(result.errorMesage)) {
                return result;
            }
        }
        throw new IllegalArgumentException("Unknown error message '" + message + "' on login");
    }
}
