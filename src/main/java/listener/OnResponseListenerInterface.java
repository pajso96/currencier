package listener;

import model.Currency;

import java.util.List;

public interface OnResponseListenerInterface {

    void onSuccess(List<Currency> currencies);
    void onFailure(int statusCode, String message);
}
