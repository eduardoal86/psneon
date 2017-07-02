package edualves.com.psneon.contacts.presenter;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edualves.com.psneon.contacts.ui.ContactView;
import edualves.com.psneon.model.ContactInfoResponse;
import edualves.com.psneon.service.Service;

/**
 * Created by edualves on 01/07/17.
 */

public class ContactPresenter {

    private final Service service;
    private final ContactView view;

    public ContactPresenter(Service service, ContactView view) {
        this.service = service;
        this.view = view;
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
