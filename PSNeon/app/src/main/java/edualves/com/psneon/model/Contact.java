package edualves.com.psneon.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by edualves on 01/07/17.
 */

public class Contact implements Serializable {

    @SerializedName("contacts")
    List<ContactInfoResponse> contacts;

    public List<ContactInfoResponse> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactInfoResponse> contacts) {
        this.contacts = contacts;
    }
}
