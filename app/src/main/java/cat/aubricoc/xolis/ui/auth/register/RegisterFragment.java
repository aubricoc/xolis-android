package cat.aubricoc.xolis.ui.auth.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cat.aubricoc.xolis.R;
import cat.aubricoc.xolis.core.enums.RegisterResult;
import cat.aubricoc.xolis.core.service.UserService;
import cat.aubricoc.xolis.ui.auth.AuthenticationActivity;
import cat.aubricoc.xolis.ui.utils.FormValidator;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.annotation.ConfirmEmail;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    @NotEmpty
    @Pattern(regex = "^[a-zA-Z0-9_]*$", messageResId = R.string.register_username_invalid)
    @Length(max = 32)
    private TextInputEditText usernameField;

    @NotEmpty
    @Email
    @Length(max = 64)
    private TextInputEditText emailField;

    @ConfirmEmail
    private TextInputEditText confirmEmailField;

    @Password(scheme = Password.Scheme.ANY, min = 4)
    @Length(max = 16)
    private TextInputEditText passwordField;

    @ConfirmPassword
    private TextInputEditText confirmPasswordField;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        usernameField = root.findViewById(R.id.register_username);
        emailField = root.findViewById(R.id.register_email);
        confirmEmailField = root.findViewById(R.id.register_confirm_email);
        passwordField = root.findViewById(R.id.register_password);
        confirmPasswordField = root.findViewById(R.id.register_confirm_password);

        FormValidator formValidator = new FormValidator(getContext(), this);
        root.findViewById(R.id.register_save).setOnClickListener(view -> formValidator.validate(v -> register()));

        return root;
    }

    private void register() {
        String username = Objects.requireNonNull(usernameField.getText()).toString();
        String email = Objects.requireNonNull(emailField.getText()).toString();
        String password = Objects.requireNonNull(passwordField.getText()).toString();
        UserService.getInstance().register(username, email, password, this::processRegisterResult);
    }

    private void processRegisterResult(RegisterResult result) {
        AuthenticationActivity activity = (AuthenticationActivity) getActivity();
        switch (result) {
            case OK:
                Objects.requireNonNull(activity).finish();
                break;
            case LOGIN_FAILED:
                Objects.requireNonNull(activity).goToLogin();
                break;
            case USERNAME_CONFLICT:
                usernameField.setError(getString(R.string.register_username_already_used));
                break;
            case EMAIL_CONFLICT:
                emailField.setError(getString(R.string.register_email_already_used));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + result);
        }
    }
}
