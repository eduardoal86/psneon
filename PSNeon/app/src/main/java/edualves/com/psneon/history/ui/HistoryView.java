package edualves.com.psneon.history.ui;

import java.util.List;

import edualves.com.psneon.model.TransferResponse;

/**
 * Created by edualves on 03/07/17.
 */

public interface HistoryView {

    void populateTransferList(List<TransferResponse> transferResponses);
}
