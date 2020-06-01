package at.ac.univie.hci.findmeaseat.ui.bookings;

import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.util.UUID;

import at.ac.univie.hci.findmeaseat.R;
import at.ac.univie.hci.findmeaseat.model.booking.Booking;
import at.ac.univie.hci.findmeaseat.model.booking.BookingService;
import at.ac.univie.hci.findmeaseat.model.booking.BookingServiceFactory;
import at.ac.univie.hci.findmeaseat.ui.bookings.BookingFragment.BookingFragmentContext;

public class BookingDetailsActivity extends AppCompatActivity implements BookingFragmentContext {

    public static final String BOOKING_ID_EXTRA_NAME = "bookingId";

    private final BookingService bookingService = BookingServiceFactory.getSingletonInstance();
    private Booking booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        UUID bookingId = UUID.fromString(getIntent().getStringExtra(BOOKING_ID_EXTRA_NAME));
        booking = bookingService.getBookingById(bookingId);
        setTitle("Buchungsdetails");
        if (findViewById(R.id.booking_details_booking_container) != null) {
            BookingFragment fragment = BookingFragment.newInstance(bookingId);
            getSupportFragmentManager().beginTransaction().add(R.id.booking_details_booking_container, fragment).commit();
        }
        Button cancelBookingButton = findViewById(R.id.cancelBookingButton);
        if (LocalDateTime.now().isBefore(booking.getStart())) {
            cancelBookingButton.setText(R.string.cancel_booking);
        } else if (LocalDateTime.now().isBefore(booking.getEnd())) {
            cancelBookingButton.setText(R.string.stop_booking);
        } else {
            cancelBookingButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public Booking getBooking(UUID bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    public void cancelBooking(View view) {
        bookingService.cancelBooking(booking);
        String message;
        if (LocalDateTime.now().isBefore(booking.getStart())) {
            message = "Buchung wurde storniert";
        } else {
            message = "Buchung wurde beendet";
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        finish();
    }

}
