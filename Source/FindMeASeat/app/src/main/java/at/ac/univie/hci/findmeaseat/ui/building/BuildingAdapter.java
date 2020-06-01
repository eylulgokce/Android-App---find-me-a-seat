package at.ac.univie.hci.findmeaseat.ui.building;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import at.ac.univie.hci.findmeaseat.R;
import at.ac.univie.hci.findmeaseat.model.booking.Period;
import at.ac.univie.hci.findmeaseat.model.booking.status.SeatStatusService;
import at.ac.univie.hci.findmeaseat.model.building.Building;
import at.ac.univie.hci.findmeaseat.model.building.Seat;

import static java.time.LocalDateTime.now;

class BuildingAdapter extends BaseAdapter implements Filterable {

    private final Context context;
    private final List<Building> buildings;
    private List<Building> filteredBuildings;
    private final SeatStatusService seatStatusService;

    BuildingAdapter(Context context, List<Building> buildings, SeatStatusService seatStatusService) {
        this.context = context;
        this.buildings = buildings;
        this.filteredBuildings = buildings;
        this.seatStatusService = seatStatusService;
    }

    @Override
    public int getCount() {
        return filteredBuildings.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredBuildings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_building, parent, false);
        }

        TextView buildingName = convertView.findViewById(R.id.area_name);
        TextView buildingAddress = convertView.findViewById(R.id.building_list_address);
        TextView buildingFloor = convertView.findViewById(R.id.area_seat_count);

        Building buildingItem = (Building) getItem(position);

        buildingName.setText(buildingItem.getName());
        buildingAddress.setText(buildingItem.getAddress().getStreet());
        List<Seat> freeSeats = seatStatusService.getFreeSeats(buildingItem, new Period(now(), now().plusMinutes(1)));
        buildingFloor.setText(String.format(Locale.GERMAN, "%d/%d", freeSeats.size(), buildingItem.maximalSeats()));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.values = buildings;
                    filterResults.count = buildings.size();
                } else {
                    List<Building> filteredBuildings = buildings.stream().filter(
                            (building) -> buildingMatchesConstraint(building, constraint)).collect(Collectors.toList()
                    );
                    filterResults.values = filteredBuildings;
                    filterResults.count = filteredBuildings.size();
                    BuildingAdapter.this.filteredBuildings = filteredBuildings;
                }
                return filterResults;
            }

            @SuppressWarnings("unchecked cast")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredBuildings = (ArrayList<Building>) results.values;
                notifyDataSetChanged();
            }
        };

    }

    private boolean buildingMatchesConstraint(Building building, CharSequence constraint) {
        String sanitizedConstraint = constraint.toString().toLowerCase();
        return building.getName().toLowerCase().contains(sanitizedConstraint) || building.getAddress().getStreet().toLowerCase().contains(sanitizedConstraint);
    }

}
