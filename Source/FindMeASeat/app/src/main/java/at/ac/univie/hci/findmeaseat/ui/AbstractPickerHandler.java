package at.ac.univie.hci.findmeaseat.ui;

import android.content.Context;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class AbstractPickerHandler {

    private LocalDateTime date;
    private TextView dateView;
    private final DateTimeFormatter dateTimeFormatter;
    private final DateChangeListener dateChangeListener;
    final Context context;

    AbstractPickerHandler(Context context, LocalDateTime initialDate, DateTimeFormatter dateTimeFormatter, DateChangeListener dateChangeListener) {
        this.date = initialDate;
        this.dateTimeFormatter = dateTimeFormatter;
        this.dateChangeListener = dateChangeListener;
        this.context = context;
    }

    abstract void openPicker();

    LocalDateTime getDate() {
        return date;
    }

    void notifyDateChange(LocalDateTime updatedDate) {
        this.date = updatedDate;
        updateViewText();
        dateChangeListener.onChange(date);
    }

    public void setDateView(TextView view) {
        this.dateView = view;
        updateViewText();
        dateView.setOnClickListener(v -> openPicker());
    }

    public interface DateChangeListener {
        void onChange(LocalDateTime updatedDate);
    }

    private void updateViewText() {
        dateView.setText(dateTimeFormatter.format(date));
    }

}
