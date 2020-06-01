package at.ac.univie.hci.findmeaseat.ui.bookings;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

import at.ac.univie.hci.findmeaseat.model.booking.Booking;
import at.ac.univie.hci.findmeaseat.ui.bookings.BookingViewHolderFactory.BookingCardViewHolder;

class BookingsAdapter extends RecyclerView.Adapter<BookingCardViewHolder> {

    private final ArrayList<Booking> bookings;
    private final BookingViewHolderFactory bookingViewHolderFactory;
    private final SelectBookingHandler selectBookingHandler;

    BookingsAdapter(Collection<Booking> bookings, LayoutInflater inflater, SelectBookingHandler selectBookingHandler) {
        this.bookings = new ArrayList<>(bookings);
        this.bookingViewHolderFactory = new BookingViewHolderFactory(inflater);
        this.selectBookingHandler = selectBookingHandler;
    }

    @NonNull
    @Override
    public BookingCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return bookingViewHolderFactory.createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingCardViewHolder holder, int position) {
        Booking booking = bookings.get(position);
        holder.bindBooking(booking);
        holder.getBookingCardView().setOnClickListener(view -> selectBookingHandler.onSelect(booking));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    interface SelectBookingHandler {
        void onSelect(Booking booking);
    }

}
