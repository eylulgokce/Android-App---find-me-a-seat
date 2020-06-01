package at.ac.univie.hci.findmeaseat.ui.bookings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import at.ac.univie.hci.findmeaseat.R;
import at.ac.univie.hci.findmeaseat.model.booking.Booking;
import at.ac.univie.hci.findmeaseat.model.booking.BookingService;
import at.ac.univie.hci.findmeaseat.model.booking.BookingServiceFactory;

public class RecentBookingsFragment extends Fragment implements BookingsAdapter.SelectBookingHandler {

    private final BookingService bookingService = BookingServiceFactory.getSingletonInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recent_bookings, container, false);
        RecyclerView bookingsRecyclerView = root.findViewById(R.id.bookingsListView);

        TextView noRecentBookingsTextView = root.findViewById(R.id.no_recent_bookings);
        if (bookingService.getCurrentBookings().isEmpty()) {
            noRecentBookingsTextView.setText(R.string.no_recent_bookings_text);
        }

        BookingsAdapter adapter = new BookingsAdapter(bookingService.getCurrentBookings(), inflater, this);
        bookingsRecyclerView.setAdapter(adapter);
        LayoutManager layoutManager = new LinearLayoutManager(getContext());
        bookingsRecyclerView.setLayoutManager(layoutManager);
        return root;
    }

    @Override
    public void onSelect(Booking booking) {
        Intent intent = new Intent(getContext(), BookingDetailsActivity.class);
        intent.putExtra(BookingDetailsActivity.BOOKING_ID_EXTRA_NAME, booking.getId().toString());
        startActivity(intent);
    }

}
