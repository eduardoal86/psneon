package edualves.com.psneon.contacts.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edualves.com.psneon.R;

/**
 * Created by edualves on 02/07/17.
 */

public class CustomDialog extends DialogFragment {

    @BindView(R.id.edittext_cash)
    EditText editTextCash;

    @BindView(R.id.confirm_dialog_btn_positive)
    Button btnPositive;

    @BindView(R.id.close_dialog)
    ImageView closeDialog;

    @BindView(R.id.name_dialog)
    TextView nameDialog;

    @BindView(R.id.phone_dialog)
    TextView phoneDialog;

    @BindView(R.id.photo_dialog)
    CircleImageView photoDialog;

    private View.OnClickListener positiveClickListener;
    private View.OnClickListener closeDialogClickListener;

    private EditTextDialogListener dialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        String positiveButtonTitle = bundle.getString("positiveButtonTitle");
        String name = bundle.getString("name");
        String phone = bundle.getString("phone");
        String imagePath = bundle.getString("imageRes");

        btnPositive.setText(positiveButtonTitle);


        btnPositive.setOnClickListener(positiveClickListener);

        closeDialog.setOnClickListener(closeDialogClickListener);

        if (positiveButtonTitle == null || positiveButtonTitle.isEmpty()) {
            btnPositive.setVisibility(View.GONE);
        }

        nameDialog.setText(name);

        phoneDialog.setText(phone);

        Glide.with(getActivity())
                .load(imagePath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(android.R.drawable.star_on)
                .into(photoDialog);

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.dialogListener = (EditTextDialogListener) context;
    }

    @Override
    public void onDetach() {
        this.dialogListener = null;
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.onFinishSendCash(editTextCash.getText().toString());
                dismiss();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
    }


    void setPositiveClickListener(@NonNull View.OnClickListener positiveClickListener) {
        this.positiveClickListener = positiveClickListener;
    }


    private void setCloseDialogClickListener(@NonNull View.OnClickListener closeDialogClickListener) {
        this.closeDialogClickListener = closeDialogClickListener;
    }

    public static class Builder {
        private String nameDialog;
        private String positiveButtonTitle;
        private String phoneDialog;
        private String imageRes;
        private View.OnClickListener positiveClickListener;
        private View.OnClickListener closeClickListener;


        public Builder withNameDialog(@NonNull final String nameDialog) {
            this.nameDialog = nameDialog;
            return this;
        }

        public Builder withPositiveButtonTitle(@NonNull final String positiveButtonTitle) {
            this.positiveButtonTitle = positiveButtonTitle;
            return this;
        }

        public Builder withPhoneDialog(@NonNull final String phoneDialog) {
            this.phoneDialog = phoneDialog;
            return this;
        }

        public Builder withImageRes(String imageRes) {
            this.imageRes = imageRes;
            return this;
        }

        public Builder withPositiveClickListener(@NonNull final View.OnClickListener positiveClickListener) {
            this.positiveClickListener = positiveClickListener;
            return this;
        }

        public Builder withCloseClickListener(@NonNull final View.OnClickListener closeClickListener) {
            this.closeClickListener = closeClickListener;
            return this;
        }

        public CustomDialog build() {
            final CustomDialog dialog = new CustomDialog();

            Bundle args = new Bundle();
            args.putString("positiveButtonTitle", positiveButtonTitle);
            args.putString("phone", phoneDialog);
            args.putString("imageRes", imageRes);
            args.putString("name", nameDialog);
            dialog.setArguments(args);

            dialog.setPositiveClickListener(positiveClickListener);
            dialog.setCloseDialogClickListener(closeClickListener);

            return dialog;
        }
    }

    public interface EditTextDialogListener {
        void onFinishSendCash(String inputCash);
    }

}
