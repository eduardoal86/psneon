package edualves.com.psneon.service;

import java.util.List;

import edualves.com.psneon.model.TransferCommand;
import edualves.com.psneon.model.TransferResponse;
import retrofit2.http.Body;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by edualves on 30/06/17.
 */

public class Service {

    private final NetworkService service;

    public Service(NetworkService service) {
        this.service = service;
    }

    public Observable<String> getUserToken(String userName, String userEmail) {

             return service.getToken(userName, userEmail)
                     .subscribeOn(Schedulers.newThread())
                     .observeOn(AndroidSchedulers.mainThread())
                     .onErrorResumeNext(new Func1<Throwable, Observable<? extends String>>() {
                         @Override
                         public Observable<? extends String> call(Throwable throwable) {
                             return Observable.error(throwable);
                         }
                     });
    }

    public Observable<Boolean> sendMoney(TransferCommand transferCommand) {

        return service.sendMoney(transferCommand)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Boolean>>() {
                    @Override
                    public Observable<? extends Boolean> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                });
    }

    public Observable<List<TransferResponse>> getTransfers(String token) {
        return service.getTransfers(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<TransferResponse>>>() {
                    @Override
                    public Observable<? extends List<TransferResponse>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                });
    }
}
