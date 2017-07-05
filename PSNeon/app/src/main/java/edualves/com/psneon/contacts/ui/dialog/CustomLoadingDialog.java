package edualves.com.psneon.contacts.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edualves.com.psneon.R;

/**
 * Created by edualves on 05/07/17.
 */

public class CustomLoadingDialog extends DialogFragment {

    @BindView(R.id.loading_image)
    ImageView loadingImg;

    @BindView(R.id.loading_title)
    TextView loadingMsg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        String message = bundle.getString("message");

        loadingMsg.setText(message);

        rotate(loadingImg);

        return view;
    }

    @Override
    public void onResume() {

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        super.onResume();

    }

    private void rotate(ImageView loading) {
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        loading.setAnimation(rotate);
    }

    public static class Builder {

        private String message;

        public Builder withMessage(@NonNull final String message) {
            this.message = message;
            return this;
        }

        public CustomLoadingDialog build() {
            final CustomLoadingDialog dialog = new CustomLoadingDialog();

            Bundle args = new Bundle();
            args.putString("message", message);
            dialog.setArguments(args);

            return dialog;
        }
    }
}
