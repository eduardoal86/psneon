package edualves.com.psneon.service;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by edualves on 30/06/17.
 */

public interface NetworkService {

    @GET("GenerateToken")
    Observable<String> getToken(@Query("nome") String userName,
                                @Query("email") String userEmail);
}
