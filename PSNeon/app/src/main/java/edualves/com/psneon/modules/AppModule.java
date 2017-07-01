package edualves.com.psneon.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by edualves on 01/07/17.
 */

@Module
public class AppModule {

    Application application;

    public AppModule(Application application) {

        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }
}
