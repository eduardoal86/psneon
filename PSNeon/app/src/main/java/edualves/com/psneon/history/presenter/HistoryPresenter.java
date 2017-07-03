package edualves.com.psneon.history.presenter;

import java.util.List;

import edualves.com.psneon.history.ui.HistoryView;
import edualves.com.psneon.model.TransferResponse;
import edualves.com.psneon.service.Service;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by edualves on 03/07/17.
 */

public class HistoryPresenter {

    private final Service service;
    private final HistoryView view;

    public HistoryPresenter(Service service, HistoryView view) {
        this.service = service;
        this.view = view;

    }

    public void getHistoryTransfer(String token) {

        Observable subcription = service.getTransfers(token);

        subcription.subscribe(new Subscriber<List<TransferResponse>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<TransferResponse> transferResponseList) {
                //TODO call activity through view
            }
        });


    }

}
