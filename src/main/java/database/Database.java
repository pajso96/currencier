package database;


import model.Setting;
import model.Currency;
import utils.ViewLogger;

import java.sql.*;


public class Database {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:dz_currencier.db";

    private Connection conn;
    private Statement statement;

    private static Database instance;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private Database() {
        checkDriver();

        try {
            conn = DriverManager.getConnection(DB_URL);
            statement = conn.createStatement();

            createTables();

        } catch (SQLException e) {
            ViewLogger.getInstance().showCritical("SQLite exception.");
            e.printStackTrace();
        }
    }

    public ResultSet fetchResults(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public void execute(String query) throws SQLException {
        statement.execute(query);
    }

    private void createTables() throws SQLException {
        statement.execute(Currency.getCreateTableSQL());
        statement.execute(Setting.getCreateTableSQL());
    }

    public static void checkDriver() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            ViewLogger.getInstance().showCritical("SQLite not found. For first install the driver.");
            e.printStackTrace();
        }

    }
}
