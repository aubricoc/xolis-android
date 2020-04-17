package cat.aubricoc.xolis.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String ACCESS_TOKEN = "xolis_auth_access_token";
    public static final String REFRESH_TOKEN = "xolis_auth_refresh_token";
    public static final String AUTH_USERNAME = "xolis_auth_username";
    public static final String LAST_MAIN_DESTINATION = "xolis_last_main_destination";

    private static final String PREFERENCES_KEY = "xolis_preferences";

    private final SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public void clear(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public Integer getInteger(String key) {
        int value = sharedPreferences.getInt(key, -1);
        return value == -1 ? null : value;
    }

    public void store(String key, String value) {
        store(value, editor -> editor.putString(key, value));
    }

    public void store(String key, Integer value) {
        store(value, editor -> editor.putInt(key, value));
    }

    private <T> void store(T value, Callback<SharedPreferences.Editor> editorConsumer) {
        if (value != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editorConsumer.execute(editor);
            editor.apply();
        }
    }
}
