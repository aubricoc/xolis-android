package cat.aubricoc.xolis.ui.auth.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cat.aubricoc.xolis.R;
import cat.aubricoc.xolis.core.service.UserService;
import cat.aubricoc.xolis.ui.auth.AuthenticationActivity;
import cat.aubricoc.xolis.ui.utils.FormValidator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

public class RegisterFragment extends Fragment {

    @NotEmpty
    @Pattern(regex = "^[a-zA-Z0-9_]*$", messageResId = R.string.register_username_invalid)
    private EditText usernameField;

    @Password(scheme = Password.Scheme.ANY, min = 1)
    private EditText passwordField;

    @ConfirmPassword
    private EditText confirmPasswordField;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        usernameField = root.findViewById(R.id.register_username);
        passwordField = root.findViewById(R.id.register_password);
        confirmPasswordField = root.findViewById(R.id.register_confirm_password);

        FormValidator formValidator = new FormValidator(getContext(), this);
        root.findViewById(R.id.register_save).setOnClickListener(view -> formValidator.validate(v -> save()));

        return root;
    }

    private void save() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        UserService.getInstance().register(username, password, this::finish);
    }

    private void finish(boolean logged) {
        AuthenticationActivity activity = (AuthenticationActivity) getActivity();
        if (activity != null) {
            if (logged) {
                activity.finish();
            } else {
                activity.goToLogin();
            }
        }
    }
}
