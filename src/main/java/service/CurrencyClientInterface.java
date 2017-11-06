package service;

import listener.OnResponseListenerInterface;

import java.util.Date;

public interface CurrencyClientInterface {

    void setOnResponseListener(OnResponseListenerInterface onResponseListener);
    void handleResults(Date date);
    void handleResults();
}
