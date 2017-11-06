package controllers;

import app.Configuration;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;
import factory.IntervalDayCellFactory;
import listener.OnResponseListenerInterface;
import model.Currency;
import service.CurrencyClientFactory;
import service.CurrencyClientInterface;
import utils.ViewLogger;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class StartController {

    @FXML
    private DatePicker intervalFromPicker;
    @FXML
    private ChoiceBox currencyServiceChoice;
    @FXML
    private TextField refreshTimeHours, refreshTimeMinutes, refreshTimeSeconds;
    @FXML
    private CheckBox refreshInterval, currenciesByService;
    @FXML
    private ListView currenciesListView;
    @FXML
    private Hyperlink currencyServiceInfoLink;

    @FXML
    private ObservableList<String> currenciesList;

    private CurrencyClientFactory currencyClientFactory;

    public StartController() {
        currencyClientFactory = new CurrencyClientFactory();
    }

    @FXML
    public void initialize() {
        initServiceChoiceBox();
        initDatePickers();
        initRefreshIntervalBox();
        initCurrenciesList();
    }

    @FXML
    private void onClientTestButtonClicked(){
        String selectedService = (String) currencyServiceChoice.getSelectionModel().getSelectedItem();
        try {
            CurrencyClientInterface currencyClient = currencyClientFactory.create(selectedService);
            currencyClient.setOnResponseListener(new OnResponseListenerInterface() {
                @Override
                public void onSuccess(List<Currency> currencies) {
                    if(currencies.isEmpty()){
                        ViewLogger.getInstance().showLog("Test passed, but cannot parse currencies from response.");
                    }else {
                        ViewLogger.getInstance().showLog("Test passed. First currency in list is:" + currencies.get(0).getCode());
                    }
                }
                @Override
                public void onFailure(int statusCode, String message) {
                    ViewLogger.getInstance().showLog(message + "\nThe response code is:" + Integer.toString(statusCode));
                }
            });

            currencyClient.handleResults();
        } catch (Exception e) {
            ViewLogger.getInstance().showLog("Currency Client with name " + selectedService + " not implemented");
            e.printStackTrace();
        }
    }

    private void initCurrenciesList() {
        currenciesList = FXCollections
                .observableList(new ArrayList(Arrays.asList(Configuration.currencies)));
        currenciesListView.setItems(currenciesList);
        currenciesListView.setCellFactory(CheckBoxListCell.forListView((Callback<String, ObservableValue<Boolean>>) item -> {
            BooleanProperty observable = new SimpleBooleanProperty();
            observable.setValue(true);
            return observable;
        }));

        currenciesListView.setDisable(true);

        currenciesByService.setSelected(true);
        currenciesByService.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            currenciesListView.setDisable(t1);
        });
    }

    private void initRefreshIntervalBox() {
        refreshInterval.selectedProperty().addListener((observableValue, aBoolean, t1) -> {

            refreshTimeHours.setDisable(!t1);
            refreshTimeMinutes.setDisable(!t1);
            refreshTimeSeconds.setDisable(!t1);

        });
    }

    private void initDatePickers() {
        Callback datePicker = new IntervalDayCellFactory().getDayCellFactory();
        intervalFromPicker.setDayCellFactory(datePicker);

        Calendar calendar = Calendar.getInstance();

        intervalFromPicker.setValue(LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) -1, calendar.get(Calendar.DAY_OF_MONTH)));
    }

    private void initServiceChoiceBox() {
        ObservableList serviceList = FXCollections
                .observableList(new ArrayList(Arrays.asList(Configuration.currencyServices)));

        currencyServiceChoice.setItems(serviceList);
        currencyServiceChoice.getSelectionModel().selectFirst();

        currencyServiceChoice.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            System.out.println(t1);
        });

        currencyServiceInfoLink.setOnAction(actionEvent -> {
            String selectedServiceName = (String) currencyServiceChoice.getSelectionModel().selectedItemProperty().get();
            if (Configuration.currencyServicesLinks.containsKey(selectedServiceName)) {
                String link = Configuration.currencyServicesLinks.get(selectedServiceName);
                runLink(link);
            } else {
                ViewLogger.getInstance().showLog("This service has no website set.");
            }
        });
    }

    private void runLink(String link) {
        try {
            new ProcessBuilder("x-www-browser", link).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
