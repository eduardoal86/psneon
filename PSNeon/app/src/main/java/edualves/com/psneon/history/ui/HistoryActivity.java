package edualves.com.psneon.history.ui;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edualves.com.psneon.BaseApp;
import edualves.com.psneon.R;
import edualves.com.psneon.history.presenter.HistoryPresenter;
import edualves.com.psneon.model.ContactInfoResponse;
import edualves.com.psneon.model.TransferResponse;
import edualves.com.psneon.service.Service;
import edualves.com.psneon.utils.Utils;

/**
 * Created by edualves on 03/07/17.
 */

public class HistoryActivity extends AppCompatActivity implements HistoryView{

    @BindView(R.id.recycler_transfers)
    RecyclerView recyclerView;

    @BindView(R.id.bar_chart)
    BarChart barChart;

    @Inject
    Service service;

    @Inject
    SharedPreferences prefs;

    HistoryPresenter presenter;

    HistoryAdapter adapter;

    List<ContactInfoResponse> contacts = new ArrayList<>();

    Map<ContactInfoResponse, Double> chartData = new HashMap<>();

    List<BarEntry> barEntries = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_history);

        ((BaseApp) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);

        //TODO display wait before these methods
        configListTransfer();
        setupListContacts();
        setupListTransfers();
    }

    private void setupListContacts() {
        contacts = presenter.loadContacts(Utils.readJson(getApplicationContext()));

    }

    private void setupListTransfers() {
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
    }

    private void configListTransfer() {
        presenter = new HistoryPresenter(service, this);
        presenter.getHistoryTransfer(prefs.getString("token", null));

    }

    private void setupBarChart(List<ContactInfoResponse> contacts, List<TransferResponse> transfers) {
        double amountId = 0;

        //TODO Move this 'for' to presenter layer, returning just a map then populate the chart
        for (int c = 0; c < contacts.size(); c++) {
            for (int i = 0; i < transfers.size(); i++) {
                if (contacts.get(c).getId() == transfers.get(i).getClienteId()) {
                    amountId += transfers.get(i).getValor();
                }
            }
            Log.d("Amount", "Amount by ID: " + contacts.get(c).getId() + " Total: " + amountId);
            if (amountId > 0) {
                chartData.put(contacts.get(c), amountId);
            }
            amountId = 0;
        }
        Log.d("Amount", "Map size " + chartData.size());

        addBarEntries(chartData);

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setDrawValues(true);
        barDataSet.setValueTextSize(10);
        barDataSet.setValueTextColor(Color.rgb(48,131,123));
        barDataSet.setForm(Legend.LegendForm.EMPTY);
        barDataSet.setColor(Color.rgb(84,247,231));

        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawLabels(false);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setDrawLabels(false);


        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.03f);


        barChart.setData(barData);
        barChart.setFitBars(false);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(true);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawTopYLabelEntry(false);
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.invalidate();

    }

    private void addBarEntries(Map<ContactInfoResponse, Double> chartData) {

        float index = 0;
        for (Double amount : chartData.values()) {
            barEntries.add(new BarEntry(index, Double.valueOf(amount).floatValue()));
            index++;
        }

    }

    @Override
    public void populateTransferList(List<TransferResponse> transferResponses) {
        adapter = new HistoryAdapter(this, transferResponses);
        adapter.setContactList(contacts);
        recyclerView.setAdapter(adapter);
        setupBarChart(contacts, transferResponses);

    }

    @Override
    public void displayErrorMessageLoadList(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
