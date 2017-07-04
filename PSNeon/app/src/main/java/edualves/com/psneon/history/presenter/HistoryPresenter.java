package edualves.com.psneon.history.presenter;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edualves.com.psneon.history.ui.HistoryView;
import edualves.com.psneon.model.ContactInfoResponse;
import edualves.com.psneon.model.TransferResponse;
import edualves.com.psneon.service.Service;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by edualves on 03/07/17.
 */

public class HistoryPresenter {

    private final Service service;
    private final HistoryView view;

    public HistoryPresenter(Service service, HistoryView view) {
        this.service = service;
        this.view = view;

    }

    public void getHistoryTransfer(String token) {

        Observable subcription = service.getTransfers(token);

        subcription.subscribe(new Subscriber<List<TransferResponse>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.displayErrorMessageLoadList(e.getMessage());
            }

            @Override
            public void onNext(List<TransferResponse> transferResponseList) {
                //TODO call activity through view
                view.populateTransferList(transferResponseList);
            }
        });


    }

    public List<ContactInfoResponse> loadContacts(String json) {

        List<ContactInfoResponse> contactList = new ArrayList<>();
        Gson gson = new Gson();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("contacts");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject exploreObject = jsonArray.getJSONObject(i);
                contactList.add(i, gson.fromJson(exploreObject.toString(), ContactInfoResponse.class));
            }

            return contactList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
