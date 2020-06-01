package at.ac.univie.hci.findmeaseat.ui.building;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import at.ac.univie.hci.findmeaseat.R;
import at.ac.univie.hci.findmeaseat.model.booking.Period;
import at.ac.univie.hci.findmeaseat.model.booking.status.SeatStatusService;
import at.ac.univie.hci.findmeaseat.model.building.Area;
import at.ac.univie.hci.findmeaseat.model.building.Seat;

class AreasAdapter extends BaseAdapter {

    private final Context context;
    private final List<Area> areas;
    private final SeatStatusService seatStatusService;
    private Period period;

    AreasAdapter(Context context, List<Area> areas, SeatStatusService seatStatusService, Period period) {
        this.context = context;
        this.areas = areas;
        this.seatStatusService = seatStatusService;
        this.period = period;
    }

    void updatePeriod(Period period) {
        this.period = period;
    }

    @Override
    public int getCount() {
        return areas.size();
    }

    @Override
    public Object getItem(int position) {
        return areas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_area, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.area_name);
        TextView seatCountTextView = convertView.findViewById(R.id.area_seat_count);

        Area area = (Area) getItem(position);

        nameTextView.setText(area.getName());
        List<Seat> freeSeats = seatStatusService.getFreeSeats(area, period);
        seatCountTextView.setText(String.format(Locale.GERMAN, "%d/%d", freeSeats.size(), area.getAllSeats().size()));
        return convertView;
    }

}
