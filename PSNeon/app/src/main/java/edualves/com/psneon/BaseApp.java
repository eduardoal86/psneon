package edualves.com.psneon;

import android.app.Application;
import edualves.com.psneon.deps.DaggerDeps;
import edualves.com.psneon.deps.Deps;
import edualves.com.psneon.modules.AppModule;
import edualves.com.psneon.modules.PrefsModule;
import edualves.com.psneon.service.NetworkModule;

/**
 * Created by edualves on 30/06/17.
 */

public class BaseApp extends Application {

    Deps deps;

    @Override
    public void onCreate() {
        super.onCreate();

        deps = DaggerDeps.builder()
                .networkModule(new NetworkModule())
                .prefsModule(new PrefsModule())
                .appModule(new AppModule(this))
                .build();
    }

    public Deps getDeps() {
        return deps;
    }
}
