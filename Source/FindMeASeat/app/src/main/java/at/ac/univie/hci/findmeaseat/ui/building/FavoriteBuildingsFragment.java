package at.ac.univie.hci.findmeaseat.ui.building;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import at.ac.univie.hci.findmeaseat.R;
import at.ac.univie.hci.findmeaseat.model.booking.status.SeatStatusService;
import at.ac.univie.hci.findmeaseat.model.booking.status.SeatStatusServiceFactory;
import at.ac.univie.hci.findmeaseat.model.building.Building;
import at.ac.univie.hci.findmeaseat.model.user.favorite.FavoriteService;
import at.ac.univie.hci.findmeaseat.model.user.favorite.FavoriteServiceFactory;

public class FavoriteBuildingsFragment extends Fragment {

    private final FavoriteService favoriteService = FavoriteServiceFactory.getSingletonInstance();
    private final SeatStatusService seatStatusService = SeatStatusServiceFactory.getSingletonInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite_buildings, container, false);

        TextView noFavoritesTextView = root.findViewById(R.id.no_favorites_message);
        if(favoriteService.getAllFavorites().isEmpty()){
            noFavoritesTextView.setText(R.string.no_favorite_buildings_text);
        }

        final ListView buildings = root.findViewById(R.id.favorite_building_list);
        BuildingAdapter buildingAdapter = new BuildingAdapter(getContext(), favoriteService.getAllFavorites(), seatStatusService);
        buildings.setAdapter(buildingAdapter);

        buildings.setOnItemClickListener((adapter, view, position, id) -> {
            Intent intent = new Intent(requireActivity(), BuildingDetailsActivity.class);
            Building building = (Building) adapter.getItemAtPosition(position);
            intent.putExtra(BuildingDetailsActivity.BUILDING_ID_EXTRA_NAME, building.getId().toString());
            startActivity(intent);
        });

        return root;
    }

}
