package service;

import builder.CurrencyFromFixerIOJSONBuilder;
import javafx.application.Platform;
import listener.OnResponseListenerInterface;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FixerIOClient implements  CurrencyClientInterface{

    private final static String URL = "https://api.fixer.io/";

    private OnResponseListenerInterface onResponseListener;

    public void handleResults() {
        handleResults(new Date());
    }

    public void setOnResponseListener(OnResponseListenerInterface onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    public void handleResults(Date date) {
        if(!ConnectionTester.isConnected()){
            onResponseListener.onFailure(404, "Connection not established. Are you connected to network?");
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);

        String url = URL + dateString + "?base=USD";

        HttpGet httpGet = new HttpGet(url);
        ResponseHandler responseHandler = httpResponse -> {
            int status = httpResponse.getStatusLine().getStatusCode();

            if(status >= 200 && status < 300){
                HttpEntity entity = httpResponse.getEntity();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                StringBuilder stringBuilder = new StringBuilder();

                String inputString;
                while((inputString = bufferedReader.readLine()) != null){
                    stringBuilder.append(inputString);
                }

                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                CurrencyFromFixerIOJSONBuilder currencyFromFixerIOJSONBuilder = new CurrencyFromFixerIOJSONBuilder(jsonObject);
                onResponseListener.onSuccess(currencyFromFixerIOJSONBuilder.build());
                return null;
            }

            onResponseListener.onFailure(status, "Something goes wrong");

            return null;
        };

        Request request = new Request(httpGet, responseHandler);
        new Thread(request).run();
    }

    private class Request implements Runnable {
        CloseableHttpClient client;
        HttpGet httpGet;
        ResponseHandler responseHandler;

        public Request(HttpGet httpGet, ResponseHandler responseHandler) {
            client= HttpClients.createDefault();
            this.httpGet = httpGet;
            this.responseHandler = responseHandler;
        }

        @Override
        public void run() {
            try {
                client.execute(httpGet, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
