package cat.aubricoc.xolis;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class XolisContext {

    private static RequestQueue requestQueue;

    private XolisContext() {
        throw new UnsupportedOperationException("Cannot instantiate utilities class");
    }

    public static void initialize(Context appContext) {
        requestQueue = Volley.newRequestQueue(appContext);
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
