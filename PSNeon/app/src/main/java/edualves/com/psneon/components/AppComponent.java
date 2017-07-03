package edualves.com.psneon.components;

import javax.inject.Singleton;

import dagger.Component;
import edualves.com.psneon.contacts.ui.ContactActivity;
import edualves.com.psneon.main.ui.MainActivity;
import edualves.com.psneon.modules.AppModule;
import edualves.com.psneon.modules.PrefsModule;
import edualves.com.psneon.modules.NetworkModule;

/**
 * Created by edualves on 30/06/17.
 */

@Singleton
@Component(modules = {NetworkModule.class, AppModule.class, PrefsModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(ContactActivity contactActivity);
}
