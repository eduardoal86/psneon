package edualves.com.psneon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edualves.com.psneon.deps.DaggerDeps;
import edualves.com.psneon.deps.Deps;
import edualves.com.psneon.service.NetworkModule;

/**
 * Created by edualves on 30/06/17.
 */

public class BaseApp extends AppCompatActivity {

    Deps deps;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        deps = DaggerDeps.builder().networkModule(new NetworkModule()).build();
    }

    public Deps getDeps() {
        return deps;
    }
}
