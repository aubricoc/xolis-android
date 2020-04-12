package cat.aubricoc.xolis.ui.auth.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cat.aubricoc.xolis.R;
import cat.aubricoc.xolis.core.service.UserService;
import cat.aubricoc.xolis.ui.auth.AuthenticationActivity;
import cat.aubricoc.xolis.ui.utils.FormValidator;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.Objects;

public class LoginFragment extends Fragment {

    @NotEmpty
    private TextInputEditText usernameField;

    @Password(scheme = Password.Scheme.ANY, min = 1)
    private TextInputEditText passwordField;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        usernameField = root.findViewById(R.id.login_username);
        passwordField = root.findViewById(R.id.login_password);

        FormValidator formValidator = new FormValidator(getContext(), this);
        root.findViewById(R.id.login_save).setOnClickListener(view -> formValidator.validate(v -> login()));

        return root;
    }

    private void login() {
        String username = Objects.requireNonNull(usernameField.getText()).toString();
        String password = Objects.requireNonNull(passwordField.getText()).toString();
        UserService.getInstance().login(username, password, this::processLoginResult);
    }

    private void processLoginResult(boolean result) {
        AuthenticationActivity activity = (AuthenticationActivity) getActivity();
        if (result) {
            Objects.requireNonNull(activity).finish();
        } else {
            passwordField.setError(getString(R.string.login_password_failed));
        }
    }
}
