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
import utils.ViewLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class StartController {

    @FXML
    private DatePicker intervalFromPicker, intervalToPicker;
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

    @FXML
    public void initialize() {
        initServiceChoiceBox();
        initDatePickers();
        initRefreshIntervalBox();
        initCurrenciesList();
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
        intervalToPicker.setDayCellFactory(datePicker);
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
