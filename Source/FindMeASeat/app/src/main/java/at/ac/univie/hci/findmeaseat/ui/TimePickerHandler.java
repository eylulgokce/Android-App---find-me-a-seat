package at.ac.univie.hci.findmeaseat.ui;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import at.ac.univie.hci.findmeaseat.R;

public class TimePickerHandler extends AbstractPickerHandler implements TimePickerDialog.OnTimeSetListener {

    public TimePickerHandler(Context context, LocalDateTime initialDate, DateTimeFormatter dateTimeFormatter, DateChangeListener dateChangeListener) {
        super(context, initialDate, dateTimeFormatter, dateChangeListener);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        LocalDateTime updatedDate = getDate().withHour(hourOfDay).withMinute(minute);
        notifyDateChange(updatedDate);
    }

    @Override
    void openPicker() {
        LocalDateTime date = getDate();
        new TimePickerDialog(context, R.style.picker, this, date.getHour(), date.getMinute(), true).show();
    }

}
