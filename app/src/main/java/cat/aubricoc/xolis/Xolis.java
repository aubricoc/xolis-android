package cat.aubricoc.xolis;

import android.content.Context;
import android.content.Intent;
import cat.aubricoc.xolis.core.utils.Preferences;
import cat.aubricoc.xolis.ui.auth.AuthenticationActivity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Xolis {

    public static final String TAG = "xolis";

    private static Context applicationContext;
    private static RequestQueue requestQueue;
    private static String serverUrl;
    private static Preferences preferences;

    private Xolis() {
        throw new UnsupportedOperationException("Cannot instantiate utilities class");
    }

    public static void initialize(Context appContext) {
        applicationContext = appContext;
        requestQueue = Volley.newRequestQueue(appContext);
        serverUrl = appContext.getString(R.string.server_url);
        preferences = new Preferences(appContext);
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static String getServerUrl() {
        return serverUrl;
    }

    public static Preferences getPreferences() {
        return preferences;
    }

    public static void goToAuthentication() {
        Intent intent = new Intent(applicationContext, AuthenticationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        applicationContext.startActivity(intent);
    }
}
