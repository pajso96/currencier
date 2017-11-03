package app;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    public static final String FIXER_IO = "FixerIO";
    public static final String CUSTOM = "CUSTOM";

    public static final String[] currencyServices = {
            FIXER_IO, CUSTOM
    };

    public static final String[] currencies = {
        "USD", "EUR", "GBP", "INR", "AUD", "CAD", "SGD", "CHF", "MYR", "JPY", "CNY", "PLN"
    };

    public static final Map<String, String> currencyServicesLinks = new HashMap<>();

    public static void init() {
        initCurrencyServicesLinks();
    }

    private static void initCurrencyServicesLinks() {
        currencyServicesLinks.put(FIXER_IO, "http://fixer.io/");
    }

}
