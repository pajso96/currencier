package service;

import app.Configuration;

public class CurrencyClientFactory {

    public CurrencyClientInterface create(String type) throws Exception {
        if(type.equals(Configuration.FIXER_IO)){
           return new FixerIOClient();
        }

        throw new Exception("Currency client with type: " + type + " not implemented.");
    }

}
