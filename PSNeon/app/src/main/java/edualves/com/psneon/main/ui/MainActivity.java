package edualves.com.psneon.main.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edualves.com.psneon.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.user_email)
    TextView userEmail;

    @BindView(R.id.profile_photo)
    CircleImageView userPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
