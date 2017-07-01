package edualves.com.psneon.deps;

import javax.inject.Singleton;

import dagger.Component;
import edualves.com.psneon.main.ui.MainActivity;
import edualves.com.psneon.service.NetworkModule;

/**
 * Created by edualves on 30/06/17.
 */

@Singleton
@Component(modules = NetworkModule.class)
public interface Deps {

    void inject(MainActivity mainActivity);
}
