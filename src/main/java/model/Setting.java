package model;

import database.Database;
import org.jetbrains.annotations.Nullable;
import utils.SQLDateFormatter;
import utils.ViewLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Setting {

    private static Setting instance;

    private String defaultCurrency;
    private String defaultService;
    private Date fetchingTo;
    private Date lastFetch;

    public static Setting getInstance() {
        if (instance == null) {
            instance = find();
        }
        return instance;
    }

    public static void createNew(){
        instance = new Setting();
    }

    public static String getCreateTableSQL() {
        return "CREATE TABLE IF NOT EXISTS configuration" +
                "(id integer PRIMARY KEY AUTOINCREMENT," +
                "default_currency text NOT NULL," +
                "default_service text NOT NULL," +
                "fetching_to text NOT NULL," +
                "last_fetch text NOT NULL);";
    }

    private static void save(Setting setting){

        String query = "";
        try {
            Database.getInstance().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    private static Setting find() {
        String query = "SELECT * FROM configuration LIMIT 1;";
        try {
            Setting setting = null;

            ResultSet results = Database.getInstance().fetchResults(query);
            Date fetchingTo, lastFetch;
            String defaultCurrency, defaultServices;
            while (results.next()) {
                setting = new Setting();
                defaultCurrency = results.getString("default_currency");
                defaultServices = results.getString("default_service");
                fetchingTo = SQLDateFormatter.reformatDate(results.getString("fetching_to"));
                lastFetch = SQLDateFormatter.reformatDateTime(results.getString("last_fetch"));
                setting.setDefaultCurrency(defaultCurrency);
                setting.setDefaultService(defaultServices);
                setting.setFetchingTo(fetchingTo);
                setting.setLastFetch(lastFetch);
                break;
            }
            return setting;

        } catch (SQLException e) {
            ViewLogger.getInstance().showLog("Cannot load currencies");
            e.printStackTrace();
            return null;
        }
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getDefaultService() {
        return defaultService;
    }

    public void setDefaultService(String defaultService) {
        this.defaultService = defaultService;
    }

    public Date getFetchingTo() {
        return fetchingTo;
    }

    public void setFetchingTo(Date fetchingTo) {
        this.fetchingTo = fetchingTo;
    }

    public Date getLastFetch() {
        return lastFetch;
    }

    public void setLastFetch(Date lastFetch) {
        this.lastFetch = lastFetch;
    }
}
