package cat.aubricoc.xolis;

import android.app.Application;

public class XolisApplication extends Application {

    public static final String TAG = "xolis";

    @Override
    public void onCreate() {
        super.onCreate();
        XolisContext.initialize(this);
    }
}
