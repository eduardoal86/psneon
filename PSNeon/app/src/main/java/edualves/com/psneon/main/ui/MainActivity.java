package edualves.com.psneon.main.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edualves.com.psneon.BaseApp;
import edualves.com.psneon.R;
import edualves.com.psneon.main.presenter.MainPresenter;
import edualves.com.psneon.service.Service;

public class MainActivity extends BaseApp implements MainView {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.user_email)
    TextView userEmail;

    @BindView(R.id.profile_photo)
    CircleImageView userPhoto;

    MainPresenter presenter;

    @Inject
    Service service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDeps().inject(this);

        presenter = new MainPresenter(service, this);

        presenter.getUserToken(getString(R.string.user_name), getString(R.string.user_email));

        initRenderView();
        initProfileInfo();
    }

    private void initRenderView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void initProfileInfo() {
        Glide.with(this)
                .load("https://avatars0.githubusercontent.com/u/8269594?v=3&s=400")
                .into(userPhoto);
        userName.setText(getString(R.string.user_name));
        userEmail.setText(getString(R.string.user_email));


    }

    @Override
    public void getTokenSuccess(String token) {
        Log.d(LOG_TAG, "Token:" + token);
    }

    @Override
    public void failureToken(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
