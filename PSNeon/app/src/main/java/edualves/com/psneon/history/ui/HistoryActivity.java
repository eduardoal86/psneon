package edualves.com.psneon.history.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edualves.com.psneon.BaseApp;
import edualves.com.psneon.R;
import edualves.com.psneon.history.presenter.HistoryPresenter;
import edualves.com.psneon.model.TransferResponse;
import edualves.com.psneon.service.Service;

/**
 * Created by edualves on 03/07/17.
 */

public class HistoryActivity extends AppCompatActivity implements HistoryView{

    @BindView(R.id.recycler_transfers)
    RecyclerView recyclerView;

    @Inject
    Service service;

    @Inject
    SharedPreferences prefs;

    HistoryPresenter presenter;

    HistoryAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_history);

        ((BaseApp) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);

        //TODO display wait before these methods
        configListTransfer();
        setupListTransfers();

    }

    private void setupListTransfers() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
    }

    private void configListTransfer() {
        presenter = new HistoryPresenter(service, this);
        presenter.getHistoryTransfer(prefs.getString("token", null));

    }

    @Override
    public void populateTransferList(List<TransferResponse> transferResponses) {
        adapter = new HistoryAdapter(this, transferResponses);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void displayErrorMessageLoadList(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
