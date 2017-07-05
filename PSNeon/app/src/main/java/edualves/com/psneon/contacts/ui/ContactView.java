package edualves.com.psneon.contacts.ui;

/**
 * Created by edualves on 01/07/17.
 */

public interface ContactView {

    void displaySuccessTransferMessage();

    void displayErrorTransferMessage(String message);

    void showLoading();

    void hideLoading();
}
