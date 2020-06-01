package at.ac.univie.hci.findmeaseat.ui.building;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import at.ac.univie.hci.findmeaseat.R;
import at.ac.univie.hci.findmeaseat.model.booking.status.SeatStatusService;
import at.ac.univie.hci.findmeaseat.model.booking.status.SeatStatusServiceFactory;
import at.ac.univie.hci.findmeaseat.model.building.Building;
import at.ac.univie.hci.findmeaseat.model.building.service.BuildingService;
import at.ac.univie.hci.findmeaseat.model.building.service.BuildingServiceFactory;

public class BuildingFragment extends Fragment {

    private final BuildingService buildingService = BuildingServiceFactory.getSingletonInstance();
    private final SeatStatusService seatStatusService = SeatStatusServiceFactory.getSingletonInstance();

    private BuildingAdapter buildingAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_building, container, false);
        final ListView buildingList = root.findViewById(R.id.building_list);
        EditText filter = root.findViewById(R.id.filter_text);
        buildingAdapter = new BuildingAdapter(getContext(), buildingService.getAllBuildings(), seatStatusService);
        buildingList.setAdapter(buildingAdapter);

        filter.addTextChangedListener(new FilterTextWatcher());
        buildingList.setOnItemClickListener((adapter, view, position, id) -> {
            Intent intent = new Intent(requireActivity(), BuildingDetailsActivity.class);
            Building building = (Building) buildingAdapter.getItem(position);
            intent.putExtra(BuildingDetailsActivity.BUILDING_ID_EXTRA_NAME, building.getId().toString());
            startActivity(intent);
        });
        return root;
    }

    private class FilterTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            buildingAdapter.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        buildingAdapter.notifyDataSetChanged();
    }

}
