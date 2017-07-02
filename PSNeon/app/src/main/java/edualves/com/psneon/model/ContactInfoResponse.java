package edualves.com.psneon.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by edualves on 01/07/17.
 */

public class ContactInfoResponse implements Serializable {

    @SerializedName("id")
    Integer id;

    @SerializedName("url")
    String url;

    @SerializedName("name")
    String name;

    @SerializedName("phone")
    String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
