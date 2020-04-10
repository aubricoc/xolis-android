package cat.aubricoc.xolis;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class XolisContext {

    private static RequestQueue requestQueue;
    private static String serverUrl;

    private XolisContext() {
        throw new UnsupportedOperationException("Cannot instantiate utilities class");
    }

    public static void initialize(Context appContext) {
        requestQueue = Volley.newRequestQueue(appContext);
        serverUrl = appContext.getString(R.string.server_url);
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static String getServerUrl() {
        return serverUrl;
    }
}
