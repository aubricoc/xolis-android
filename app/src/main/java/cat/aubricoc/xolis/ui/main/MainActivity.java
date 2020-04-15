package cat.aubricoc.xolis.ui.main;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import cat.aubricoc.xolis.R;
import cat.aubricoc.xolis.Xolis;
import cat.aubricoc.xolis.core.service.UserService;
import cat.aubricoc.xolis.core.utils.Preferences;
import cat.aubricoc.xolis.server.model.UserAuthentication;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavController bottomNavController;
    private DrawerLayout sidebarContainer;
    private NavigationView sidebar;
    private MenuItem menuLogin;
    private MenuItem menuLogout;
    private TextView sidebarTitle;
    private TextView sidebarSubtitle;

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
        prepareAuthData();
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
        View headerView = sidebar.getHeaderView(0);
        sidebarTitle = headerView.findViewById(R.id.main_sidebar_title);
        sidebarSubtitle = headerView.findViewById(R.id.main_sidebar_subtitle);
        prepareSidebarMenu();
    }

    private void prepareSidebarMenu() {
        Menu menu = sidebar.getMenu();
        menuLogin = menu.findItem(R.id.main_menu_login);
        menuLogin.setOnMenuItemClickListener(v -> {
            Xolis.goToAuthentication();
            return false;
        });
        menuLogout = menu.findItem(R.id.main_menu_logout);
        menuLogout.setOnMenuItemClickListener(v -> {
            UserService.getInstance().clearAuthentication();
            prepareAuthData();
            return false;
        });
    }

    private void prepareBottomNavigation() {
        BottomNavigationView navView = findViewById(R.id.main_menu);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
        bottomNavController = Objects.requireNonNull(navHostFragment).getNavController();
        Integer lastDestination = Xolis.getPreferences().getInteger(Preferences.LAST_MAIN_DESTINATION);
        if (lastDestination != null) {
            bottomNavController.navigate(lastDestination);
        }
        bottomNavController.addOnDestinationChangedListener((controller, destination, arguments) -> setToolbarTitle(destination));
        NavigationUI.setupWithNavController(navView, bottomNavController);
    }

    private void prepareAuthData() {
        UserAuthentication user = UserService.getInstance().getAuthenticatedUser();
        if (user == null) {
            menuLogin.setEnabled(true);
            menuLogout.setEnabled(false);
            sidebarTitle.setText(StringUtils.EMPTY);
            sidebarSubtitle.setText(StringUtils.EMPTY);
        } else {
            menuLogin.setEnabled(false);
            menuLogout.setEnabled(true);
            sidebarTitle.setText(getString(R.string.username, user.getUsername()));
            sidebarSubtitle.setText(getString(R.string.user_profile));
        }
    }

    private void setToolbarTitle(NavDestination bottomNavDestination) {
        CharSequence label = Objects.requireNonNull(bottomNavDestination).getLabel();
        toolbar.setTitle(label);
    }
}
