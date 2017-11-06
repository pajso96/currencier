package builder;

import factory.CurrencyFactory;
import factory.CurrencyFactoryInterface;
import model.Currency;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CurrencyFromFixerIOJSONBuilder implements CurrencyListBuilderInterface {

    private List<Currency> currencyList;
    private CurrencyFactoryInterface currencyFactory;
    private Date day;

    public CurrencyFromFixerIOJSONBuilder(JSONObject jsonObject) {
        currencyList= new ArrayList<>();
        currencyFactory = new CurrencyFactory();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            day = dateFormat.parse(jsonObject.getString("date"));
            addBaseCurrencyToList(jsonObject);

            JSONObject ratesJsonObject = jsonObject.getJSONObject("rates");

            Iterator iterator = ratesJsonObject.keys();
            while(iterator.hasNext()){
                String code = (String) iterator.next();
                Double ratio = ratesJsonObject.getDouble(code);

                Currency currency = currencyFactory.create();
                currency.setDay(day);
                currency.setRatio(ratio);
                currency.setTime(day);
                currency.setCode(code);
                currency.setCheckedAt(day);

                currencyList.add(currency);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void addBaseCurrencyToList(JSONObject jsonObject){
     Currency currency = currencyFactory.create();
     currency.setDay(day);
     currency.setRatio(1.0);
     currency.setTime(day);
     currency.setCode(jsonObject.getString("base"));
     currency.setCheckedAt(day);
     currencyList.add(currency);
    }

    public List<Currency> build() {
        return currencyList;
    }
}
