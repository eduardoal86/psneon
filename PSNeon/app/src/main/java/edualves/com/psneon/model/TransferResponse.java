package edualves.com.psneon.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by edualves on 03/07/17.
 */

public class TransferResponse implements Serializable {

    @SerializedName("Id")
    private Integer id;

    @SerializedName("ClienteId")
    private Integer clienteId;

    @SerializedName("Valor")
    private Double valor;

    @SerializedName("Token")
    private String token;

    @SerializedName("Data")
    private String data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
