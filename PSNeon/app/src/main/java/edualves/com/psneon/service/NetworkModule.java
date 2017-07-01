package edualves.com.psneon.service;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by edualves on 30/06/17.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    Retrofit provideCall() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .build();

                        Response response = chain.proceed(request);
                        response.cacheResponse();

                        return response;
                    }
                }).build();

        return new Retrofit.Builder()
                .baseUrl("http://processoseletivoneon.azurewebsites.net/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkService providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Service providesService(
            NetworkService networkService) {
        return new Service(networkService);
    }
}
