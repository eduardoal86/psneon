package edualves.com.psneon.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by edualves on 01/07/17.
 */

@Module
public class PrefsModule {

    @Provides
    @Singleton
    SharedPreferences providesPrefs(Application application) {

        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
