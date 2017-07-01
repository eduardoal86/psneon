package edualves.com.psneon.main.ui;

/**
 * Created by edualves on 30/06/17.
 */

public interface MainView {

    void getTokenSuccess(String token);

    void failureToken(String errorMessage);
}
