package factory;

import model.Currency;

public class CurrencyFactory implements CurrencyFactoryInterface {
    public Currency create() {
        return new Currency();
    }
}
