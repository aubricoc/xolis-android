package cat.aubricoc.xolis.ui.main.wishes.create;

import android.os.Bundle;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cat.aubricoc.xolis.R;
import cat.aubricoc.xolis.core.service.WishService;
import cat.aubricoc.xolis.ui.utils.FormValidator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import static androidx.appcompat.app.AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR;

public class CreateWishActivity extends AppCompatActivity {

    @NotEmpty
    private EditText nameField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(FEATURE_SUPPORT_ACTION_BAR);
        setContentView(R.layout.activity_create_wish);

        nameField = findViewById(R.id.create_wish_name);

        FormValidator formValidator = new FormValidator(this, this);
        findViewById(R.id.create_wish_save).setOnClickListener(view -> formValidator.validate(v -> save()));
    }

    private void save() {
        String name = nameField.getText().toString();
        WishService.getInstance().saveNew(name, v -> finish());
    }
}
