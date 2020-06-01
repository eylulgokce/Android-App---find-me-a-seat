package at.ac.univie.hci.findmeaseat.ui.buildings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

import at.ac.univie.hci.findmeaseat.R;
import at.ac.univie.hci.findmeaseat.model.booking.Period;
import at.ac.univie.hci.findmeaseat.model.booking.status.SeatStatusService;
import at.ac.univie.hci.findmeaseat.model.building.Seat;

import static at.ac.univie.hci.findmeaseat.model.booking.status.SeatStatusService.SeatStatus.FREE;

public class SeatsAdapter extends RecyclerView.Adapter<SeatsAdapter.SeatViewHolder> {

    private final ArrayList<Seat> seats;
    private final SeatSelectionHandler seatSelectionHandler;
    private final Context context;
    private final SeatStatusService seatStatusService;
    private final Period period;

    SeatsAdapter(Collection<Seat> seats, Context context, SeatSelectionHandler seatSelectionHandler, SeatStatusService seatStatusService, Period period) {
        this.seats = new ArrayList<>(seats);
        this.seatSelectionHandler = seatSelectionHandler;
        this.context = context;
        this.seatStatusService = seatStatusService;
        this.period = period;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_seat, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        final Seat seat = seats.get(position);
        holder.parentView.setOnClickListener(v -> seatSelectionHandler.select(seat));
        holder.textView.setText(seat.getName());
        if (seatSelectionHandler.isSelected(seat)) {
            holder.textView.setBackgroundColor(context.getColor(R.color.colorAccent));
        } else if (seatStatusService.getStatus(seat, period).equals(FREE)) {
            holder.textView.setBackgroundColor(context.getColor(R.color.freeSeat));
        } else {
            holder.textView.setBackgroundColor(context.getColor(R.color.design_default_color_error));
        }
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    static class SeatViewHolder extends RecyclerView.ViewHolder {
        final View parentView;
        final TextView textView;

        SeatViewHolder(View view) {
            super(view);
            this.parentView = view;
            this.textView = view.findViewById(R.id.seat_item_text);
        }
    }

    public interface SeatSelectionHandler {

        boolean isSelected(Seat seat);

        void select(Seat seat);

    }

}
