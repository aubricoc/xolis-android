package cat.aubricoc.xolis.core.enums;

public enum RegisterResult {

    OK(null),
    USERNAME_CONFLICT("username"),
    EMAIL_CONFLICT("email"),
    LOGIN_FAILED(null);

    private final String conflictField;

    RegisterResult(String conflictField) {
        this.conflictField = conflictField;
    }

    public static RegisterResult getByConflictField(String field) {
        for (RegisterResult result : values()) {
            if (field.equals(result.conflictField)) {
                return result;
            }
        }
        throw new IllegalArgumentException("Unknown conflict field '" + field + "' on register");
    }
}
