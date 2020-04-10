package cat.aubricoc.xolis.ui.wishes.create;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cat.aubricoc.xolis.R;
import cat.aubricoc.xolis.core.service.WishService;

import static androidx.appcompat.app.AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR;

public class CreateWishActivity extends AppCompatActivity {

    private EditText nameField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(FEATURE_SUPPORT_ACTION_BAR);
        setContentView(R.layout.activity_create_wish);

        findViewById(R.id.create_wish_save).setOnClickListener(view -> save());
        nameField = findViewById(R.id.create_wish_name);
    }

    private void save() {
        String name = nameField.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, R.string.create_wish_name_required, Toast.LENGTH_SHORT).show();
        } else {
            WishService.getInstance().saveNew(name, v -> finish());
        }
    }
}
