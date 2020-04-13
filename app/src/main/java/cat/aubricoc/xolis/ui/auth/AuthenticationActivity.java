package cat.aubricoc.xolis.ui.auth;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import cat.aubricoc.xolis.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class AuthenticationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavController bottomNavController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        prepareToolbar();
        prepareBottomNavigation();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void prepareToolbar() {
        toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void prepareBottomNavigation() {
        BottomNavigationView navView = findViewById(R.id.authentication_menu);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.authentication_fragment_container);
        if (navHostFragment != null) {
            bottomNavController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(navView, bottomNavController);
            bottomNavController.addOnDestinationChangedListener((controller, destination, arguments) -> setToolbarTitle(destination));
        }
    }

    public void goToLogin() {
        if (bottomNavController != null) {
            bottomNavController.navigate(R.id.navigation_login);
        }
    }

    private void setToolbarTitle(NavDestination bottomNavDestination) {
        CharSequence label = Objects.requireNonNull(bottomNavDestination).getLabel();
        toolbar.setTitle(label);
    }
}
