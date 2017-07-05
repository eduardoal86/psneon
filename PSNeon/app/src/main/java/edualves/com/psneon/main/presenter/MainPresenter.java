package edualves.com.psneon.main.presenter;

import android.util.Log;

import edualves.com.psneon.main.ui.MainView;
import edualves.com.psneon.service.Service;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by edualves on 30/06/17.
 */

public class MainPresenter {

    private final Service service;

    private final MainView view;

    private final String LOG_TAG = MainPresenter.class.getSimpleName();

    public MainPresenter(Service service, MainView mainView) {

        this.service = service;
        this.view = mainView;
    }

    public void getUserToken(String userName, String userEmail) {

        Observable subscription = service.getUserToken(userName, userEmail);

        subscription.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(LOG_TAG, "OnError:" +  e.getMessage());
                view.failureToken(e.getMessage());
            }

            @Override
            public void onNext(String token) {
                Log.d(LOG_TAG, "OnSuccess:" + token);
                view.getTokenSuccess(token);
            }
        });
    }
}
