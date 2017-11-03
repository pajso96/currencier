package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLDateFormatter {

    public static Date reformatDate(String date){
        DateFormat dateFormat = new SimpleDateFormat("y-M-d");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date reformatTime(String date){
        DateFormat dateFormat = new SimpleDateFormat("H:m:s");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date reformatDateTime(String date){
        DateFormat dateFormat = new SimpleDateFormat("y-M-d H:m:s");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat("y-M-d");
        return dateFormat.format(date);
    }

    public static String formatTime(Date date){
        DateFormat dateFormat = new SimpleDateFormat("H:m:s");
        return dateFormat.format(date);
    }

    public static String formatDateTime(Date date){
        DateFormat dateFormat = new SimpleDateFormat("y-M-d H:m:s");
        return dateFormat.format(date);
    }

}
