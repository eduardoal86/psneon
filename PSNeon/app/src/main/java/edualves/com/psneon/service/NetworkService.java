package edualves.com.psneon.service;

import java.util.List;

import edualves.com.psneon.model.TransferCommand;
import edualves.com.psneon.model.TransferResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by edualves on 30/06/17.
 */

public interface NetworkService {

    @GET("GenerateToken")
    Observable<String> getToken(@Query("nome") String userName,
                                @Query("email") String userEmail);

    @POST("SendMoney")
    Observable<Boolean> sendMoney(@Body TransferCommand transferCommand);

    @GET("GetTransfers")
    Observable<List<TransferResponse>> getTransfers(@Query("token") String token);
}
