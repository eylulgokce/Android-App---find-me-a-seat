package at.ac.univie.hci.findmeaseat.ui;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.widget.DatePicker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import at.ac.univie.hci.findmeaseat.R;

public final class DatePickerHandler extends AbstractPickerHandler implements OnDateSetListener {

    public DatePickerHandler(Context context, LocalDateTime initialDate, DateTimeFormatter dateTimeFormatter, DateChangeListener dateChangeListener) {
        super(context, initialDate, dateTimeFormatter, dateChangeListener);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        LocalDateTime updatedDate = getDate().withYear(year).withMonth(month + 1).withDayOfMonth(dayOfMonth);
        notifyDateChange(updatedDate);
    }

    @Override
    void openPicker() {
        LocalDateTime date = getDate();
        new DatePickerDialog(context, R.style.picker, this, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth()).show();
    }

}
