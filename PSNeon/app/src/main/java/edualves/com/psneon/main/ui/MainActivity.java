package edualves.com.psneon.main.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import edualves.com.psneon.BaseApp;
import edualves.com.psneon.R;
import edualves.com.psneon.contacts.ui.ContactActivity;
import edualves.com.psneon.history.ui.HistoryActivity;
import edualves.com.psneon.main.presenter.MainPresenter;
import edualves.com.psneon.service.Service;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Inject
    Service service;

    @Inject
    SharedPreferences prefs;

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.user_email)
    TextView userEmail;

    @BindView(R.id.profile_photo)
    CircleImageView userPhoto;

    private MainPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseApp) getApplication()).getAppComponent().inject(this);

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

    @OnClick(R.id.send_cash)
    void sendCash() {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.history_transfer)
    void historyTransfers() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void getTokenSuccess(String token) {
        Log.d(LOG_TAG, "Token:" + token);
        prefs.edit().putString("token", token).apply();
    }

    @Override
    public void failureToken(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
