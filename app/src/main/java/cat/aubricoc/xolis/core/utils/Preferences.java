package cat.aubricoc.xolis.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String ACCESS_TOKEN = "xolis_secured_access_token";
    private static final String PREFERENCES_KEY = "xolis_preferences";
    private final SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public void store(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value == null) {
            editor.remove(key);
        } else {
            editor.putString(key, value);
        }
        editor.apply();
    }

    public String get(String key) {
        return sharedPreferences.getString(key, null);
    }
}
