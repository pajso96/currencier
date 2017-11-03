package model;

import database.Database;
import utils.SQLDateFormatter;
import utils.ViewLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Currency {

    private int id;
    private String code;
    private Double ratio;
    private Date day;
    private Date time;
    private Date checkedAt;

    public static String getCreateTableSQL() {
        return "CREATE TABLE IF NOT EXISTS currency" +
                "(id integer PRIMARY KEY AUTOINCREMENT," +
                "code text NOT NULL," +
                "ratio real NOT NULL," +
                "day text NOT NULL," +
                "time text NOT NULL," +
                "checked_at text NOT NULL);";
    }

    public static void testInsert(){
        try {
            Database.getInstance().execute("INSERT INTO currency values(null, 'PLN', '0.35', '1996-4-23', " +
                    "'22:00:00', '1996-04-23 22:00:00');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Currency> findByDate(Date date) {
        List<Currency> currencies = new LinkedList<Currency>();

        String query = "SELECT * FROM currency where day='" + SQLDateFormatter.formatDate(date) +
                "';";
        try {
            ResultSet results = Database.getInstance().fetchResults(query);
            Date day, time, checkedAt;
            String code;
            int id;
            Double ratio;
            while (results.next()) {
                Currency currency = new Currency();
                id = results.getInt("id");
                code = results.getString("code");
                day = SQLDateFormatter.reformatDate(results.getString("day"));
                time = SQLDateFormatter.reformatTime(results.getString("time"));
                checkedAt = SQLDateFormatter.reformatDateTime(results.getString("checked_at"));
                ratio = results.getDouble("ratio");
                currency.setCheckedAt(checkedAt);
                currency.setCode(code);
                currency.setDay(day);
                currency.setTime(time);
                currency.setRatio(ratio);
                currency.setId(id);
                currencies.add(currency);
            }
            return currencies;

        } catch (SQLException e) {
            ViewLogger.getInstance().showLog("Cannot load currencies");
            e.printStackTrace();
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(Date checkedAt) {
        this.checkedAt = checkedAt;
    }
}
