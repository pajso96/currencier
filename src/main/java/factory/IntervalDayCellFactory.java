package factory;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class IntervalDayCellFactory {

    public Callback<DatePicker, DateCell> getDayCellFactory(){
        return new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        int year=  Calendar.getInstance().get(Calendar.YEAR);
                        int month = Calendar.getInstance().get(Calendar.MONTH)+1;
                        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                        if(item.isAfter(LocalDate.of(year, month, day))){
                            setDisable(true);
                            setStyle("-fx-background-color: #FF6666;");
                        }
                    }
                };
            }
        };
    }

}
