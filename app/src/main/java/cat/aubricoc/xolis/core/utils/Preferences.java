package cat.aubricoc.xolis.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String ACCESS_TOKEN = "xolis_secured_access_token";
    public static final String AUTH_USERNAME = "xolis_auth_username";
    public static final String LAST_MAIN_DESTINATION = "xolis_last_main_destination";

    private static final String PREFERENCES_KEY = "xolis_preferences";

    private final SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public boolean has(String key) {
        return getString(key) != null;
    }

    public void clear(String key) {
        store(key, null, null);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public Integer getInteger(String key) {
        int value = sharedPreferences.getInt(key, -1);
        return value == -1 ? null : value;
    }

    public void store(String key, String value) {
        store(key, value, editor -> editor.putString(key, value));
    }

    public void store(String key, Integer value) {
        store(key, value, editor -> editor.putInt(key, value));
    }

    private <T> void store(String key, T value, Callback<SharedPreferences.Editor> editorConsumer) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value == null) {
            editor.remove(key);
        } else {
            editorConsumer.execute(editor);
        }
        editor.apply();
    }
}
