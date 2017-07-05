package edualves.com.psneon.contacts.ui;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edualves.com.psneon.BaseApp;
import edualves.com.psneon.R;
import edualves.com.psneon.contacts.presenter.ContactPresenter;
import edualves.com.psneon.contacts.ui.dialog.CustomDialog;
import edualves.com.psneon.contacts.ui.dialog.CustomLoadingDialog;
import edualves.com.psneon.main.ui.MainActivity;
import edualves.com.psneon.model.ContactInfoResponse;
import edualves.com.psneon.model.TransferCommand;
import edualves.com.psneon.service.Service;
import edualves.com.psneon.utils.Utils;

public class ContactActivity extends AppCompatActivity implements ContactView, CustomDialog.EditTextDialogListener {

    private static final String LOG_TAG = ContactActivity.class.getSimpleName();

    @BindView(R.id.recycler_contact)
    RecyclerView recyclerList;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ContactAdapter adapter;

    ContactPresenter presenter;

    @Inject
    Service service;

    @Inject
    SharedPreferences prefs;

    private TextView toolbarTitle;

    List<ContactInfoResponse> contactList = new ArrayList<>();

    private CustomDialog cashDialog;
    private CustomLoadingDialog loadingDialog;

    TransferCommand transferCommand = new TransferCommand();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ((BaseApp) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);

        configureToolbar();
        configList();
        displayContactList();

    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.send_money_title);
        toolbarTitle.setTextColor(ContextCompat.getColor(this, R.color.regular_white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void configList() {
        presenter = new ContactPresenter(service, this);
        contactList = presenter.loadContacts(Utils.readJson(getApplicationContext()));
    }

    private void displayContactList() {
        recyclerList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerList.setHasFixedSize(false);

        adapter = new ContactAdapter(
                getApplicationContext(),
                contactList,
                new ContactAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(final ContactInfoResponse contactItem) {
                        ContactActivity.downloadPhoto(ContactActivity.this, contactItem.getUrl());
                        openDialog(contactItem);
                    }
                });
        recyclerList.setAdapter(adapter);

    }

    private void openDialog(ContactInfoResponse contactItem) {

        transferCommand.setId(contactItem.getId());
        transferCommand.setToken(prefs.getString("token", null));

        cashDialog = new CustomDialog.Builder()
                                .withNameDialog(contactItem.getName())
                                .withPhoneDialog(contactItem.getPhone())
                                .withImageRes(contactItem.getUrl())
                                .withPositiveButtonTitle(getString(R.string.send_cash_btn))
                                .withCloseClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        cashDialog.dismiss();
                                    }
                                })
                                .build();
                        cashDialog.show(getSupportFragmentManager(), "dialog");

    }

    private static void downloadPhoto(ContactActivity activity, String url) {
        Glide.with(activity).load(url).downloadOnly(80, 80);
    }

    @Override
    public void displaySuccessTransferMessage() {
        Log.d(LOG_TAG, "TRANSFER_SUCESS " + prefs.getString("token", null));
        Toast.makeText(ContactActivity.this,
                R.string.money_sent_confirmation,
                Toast.LENGTH_SHORT).show();
        backToMainScreen();
    }

    @Override
    public void displayErrorTransferMessage(String message) {
        Log.d(LOG_TAG, "ERROR:" + message);
        Toast.makeText(ContactActivity.this,
                R.string.money_sent_error_message,
                Toast.LENGTH_SHORT).show();
        backToMainScreen();
    }

    private void backToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        loadingDialog = new CustomLoadingDialog.Builder()
                .withMessage(getString(R.string.loading_message))
                .build();

        loadingDialog.setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        loadingDialog.show(getSupportFragmentManager(), "loading");
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isResumed()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onFinishSendCash(String inputCash) {
        transferCommand.setValue(Double.valueOf(inputCash));
        presenter.transferMoney(transferCommand);
    }
}
