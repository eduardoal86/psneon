package edualves.com.psneon.service;

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

}
