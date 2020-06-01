package at.ac.univie.hci.findmeaseat.ui.bookings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.UUID;

import at.ac.univie.hci.findmeaseat.model.booking.Booking;
import at.ac.univie.hci.findmeaseat.ui.bookings.BookingViewHolderFactory.BookingCardViewHolder;

public class BookingFragment extends Fragment {

    private static final String ARG_BOOKING_ID = "argBookingId";

    private UUID bookingId;
    private BookingFragmentContext context;

    public BookingFragment() {
    }

    static BookingFragment newInstance(UUID bookingId) {
        BookingFragment fragment = new BookingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BOOKING_ID, bookingId.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookingId = UUID.fromString(getArguments().getString(ARG_BOOKING_ID));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (BookingFragmentContext) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BookingViewHolderFactory viewCreator = new BookingViewHolderFactory(inflater);
        BookingCardViewHolder viewHolder = viewCreator.createViewHolder(container);
        Booking booking = context.getBooking(bookingId);
        viewHolder.bindBooking(booking);
        return viewHolder.getBookingCardView();
    }

    interface BookingFragmentContext {
        Booking getBooking(UUID bookingId);
    }

}
