package cat.aubricoc.xolis.ui.main;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import cat.aubricoc.xolis.R;
import cat.aubricoc.xolis.Xolis;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavController bottomNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.main_sidebar_container);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(Gravity.START));

        BottomNavigationView navView = findViewById(R.id.main_menu);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
        if (navHostFragment != null) {
            bottomNavController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(navView, bottomNavController);
            bottomNavController.addOnDestinationChangedListener((controller, destination, arguments) -> setToolbarTitle(destination));
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setToolbarTitle(bottomNavController.getCurrentDestination());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Xolis.isAuthenticated()) {
            findViewById(R.id.main_menu_login).setVisibility(View.GONE);
            findViewById(R.id.main_menu_logout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.main_menu_login).setVisibility(View.VISIBLE);
            findViewById(R.id.main_menu_logout).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_login:
                Xolis.goToAuthentication();
                break;
            case R.id.main_menu_logout:
                Xolis.clearAuthentication();
                break;
        }
        return false;
    }

    private void setToolbarTitle(NavDestination bottomNavDestination) {
        CharSequence label = Objects.requireNonNull(bottomNavDestination).getLabel();
        toolbar.setTitle(label);
    }
}
