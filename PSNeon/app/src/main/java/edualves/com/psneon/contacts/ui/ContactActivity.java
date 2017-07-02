package edualves.com.psneon.contacts.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edualves.com.psneon.R;
import edualves.com.psneon.contacts.presenter.ContactPresenter;
import edualves.com.psneon.contacts.ui.dialog.CustomDialog;
import edualves.com.psneon.model.ContactInfoResponse;
import edualves.com.psneon.service.Service;
import edualves.com.psneon.utils.Utils;

public class ContactActivity extends AppCompatActivity implements ContactView {

    @BindView(R.id.recycler_contact)
    RecyclerView recyclerList;

    ContactAdapter adapter;

    ContactPresenter presenter;

    @Inject
    Service service;

    List<ContactInfoResponse> contactList = new ArrayList<>();

    CustomDialog cashDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);

        configList();
        displayContactList();

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
                    public void onClick(ContactInfoResponse contactItem) {

                        ContactActivity.downloadPhoto(ContactActivity.this, contactItem.getUrl());

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
                                .withPositiveClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(ContactActivity.this,
                                                "Dinheiro enviado!",
                                                Toast.LENGTH_SHORT).show();

                                        cashDialog.dismiss();
                                    }
                                })
                                .build();
                        cashDialog.show(getSupportFragmentManager(), "dialog");
                    }
                });
        recyclerList.setAdapter(adapter);

    }

    private static void downloadPhoto(ContactActivity activity, String url) {
        Glide.with(activity).load(url).downloadOnly(80, 80);
    }

}