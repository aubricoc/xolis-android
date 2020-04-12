package cat.aubricoc.xolis;

import android.app.Application;

public class XolisApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Xolis.initialize(this);
    }
}
