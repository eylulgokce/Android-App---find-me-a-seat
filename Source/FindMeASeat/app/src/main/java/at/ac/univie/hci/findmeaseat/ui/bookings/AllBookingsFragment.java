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

public class AllBookingsFragment extends Fragment implements BookingsAdapter.SelectBookingHandler {

    private final BookingService bookingService = BookingServiceFactory.getSingletonInstance();
    private boolean refreshOnResume = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_bookings, container, false);

        if (bookingService.getAllBookings().isEmpty()) {
            TextView noBookingsTextView = root.findViewById(R.id.no_bookings_message);
            noBookingsTextView.setText(R.string.no_bookings_text);
        }
        RecyclerView bookingsRecyclerView = root.findViewById(R.id.bookingsListView);
        BookingsAdapter adapter = new BookingsAdapter(bookingService.getAllBookings(), inflater, this);
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

    @Override
    public void onResume() {
        super.onResume();
        if (refreshOnResume) {
            requireActivity().finish();
            startActivity(requireActivity().getIntent());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        refreshOnResume = true;
    }

}
