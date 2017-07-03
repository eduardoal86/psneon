package edualves.com.psneon.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by edualves on 02/07/17.
 */

public class TransferCommand implements Serializable {

    @SerializedName("ClienteId")
    Integer id;

    @SerializedName("token")
    String token;

    @SerializedName("valor")
    Double value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
