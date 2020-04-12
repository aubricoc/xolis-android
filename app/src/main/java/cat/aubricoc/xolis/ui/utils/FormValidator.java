package cat.aubricoc.xolis.ui.utils;

import android.content.Context;
import cat.aubricoc.xolis.core.utils.Callback;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

public class FormValidator implements Validator.ValidationListener {

    private final Context context;
    private final Validator validator;
    private Callback<Void> successCallback;

    public FormValidator(Context context, Object controller) {
        this.context = context;
        this.validator = new Validator(controller);
        this.validator.setValidationListener(this);
    }

    public void validate(Callback<Void> onSuccess) {
        this.successCallback = onSuccess;
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        if (successCallback != null) {
            successCallback.execute(null);
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            TextInputEditText field = (TextInputEditText) error.getView();
            field.setError(error.getCollatedErrorMessage(context));
        }
    }
}
