package edualves.com.psneon;

import android.app.Application;

import edualves.com.psneon.components.AppComponent;
import edualves.com.psneon.components.DaggerAppComponent;
import edualves.com.psneon.modules.AppModule;
import edualves.com.psneon.modules.PrefsModule;
import edualves.com.psneon.modules.NetworkModule;

/**
 * Created by edualves on 30/06/17.
 */

public class BaseApp extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .prefsModule(new PrefsModule())
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
