package cat.aubricoc.xolis.ui.main;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import cat.aubricoc.xolis.R;
import cat.aubricoc.xolis.Xolis;
import cat.aubricoc.xolis.core.utils.Preferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavController bottomNavController;
    private DrawerLayout sidebarContainer;
    private NavigationView sidebar;
    private MenuItem menuLogin;
    private MenuItem menuLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareToolbar();
        prepareSidebar();
        prepareBottomNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolbarTitle(bottomNavController.getCurrentDestination());
        showOrHideMenus();
    }

    @Override
    public void onBackPressed() {
        if (sidebarContainer.isDrawerOpen(sidebar)) {
            sidebarContainer.closeDrawer(sidebar);
        } else {
            finish();
        }
    }

    private void prepareToolbar() {
        toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
    }

    private void prepareSidebar() {
        sidebarContainer = findViewById(R.id.main_sidebar_container);
        toolbar.setNavigationOnClickListener(v -> sidebarContainer.openDrawer(Gravity.START));
        sidebar = findViewById(R.id.main_sidebar);
        prepareSidebarMenu();
    }

    private void prepareSidebarMenu() {
        Menu menu = sidebar.getMenu();
        menuLogin = menu.findItem(R.id.main_menu_login);
        menuLogout = menu.findItem(R.id.main_menu_logout);
        menuLogin.setOnMenuItemClickListener(v -> {
            Xolis.goToAuthentication();
            return false;
        });
        menuLogout.setOnMenuItemClickListener(v -> {
            Xolis.clearAuthentication();
            showOrHideMenus();
            return false;
        });
    }

    private void prepareBottomNavigation() {
        BottomNavigationView navView = findViewById(R.id.main_menu);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
        bottomNavController = Objects.requireNonNull(navHostFragment).getNavController();
        Integer lastDestination = Xolis.getPreferences().getInteger(Preferences.LAST_MAIN_VIEW);
        if (lastDestination != null) {
            bottomNavController.navigate(lastDestination);
        }
        bottomNavController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            setToolbarTitle(destination);
            Xolis.getPreferences().store(Preferences.LAST_MAIN_VIEW, destination.getId());
        });
        NavigationUI.setupWithNavController(navView, bottomNavController);
    }

    private void showOrHideMenus() {
        if (Xolis.isAuthenticated()) {
            menuLogin.setVisible(false);
            menuLogout.setVisible(true);
        } else {
            menuLogin.setVisible(true);
            menuLogout.setVisible(false);
        }
    }

    private void setToolbarTitle(NavDestination bottomNavDestination) {
        CharSequence label = Objects.requireNonNull(bottomNavDestination).getLabel();
        toolbar.setTitle(label);
    }
}
