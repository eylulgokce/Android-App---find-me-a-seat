package at.ac.univie.hci.findmeaseat.model.building.service;

import android.content.Context;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import at.ac.univie.hci.findmeaseat.model.building.AreaSeatGenerator;
import at.ac.univie.hci.findmeaseat.model.building.Building;

public class DummyBuildingService implements BuildingService {

    private final Map<UUID, Building> buildings = new HashMap<>();
    private final AreaSeatGenerator seatGenerator = new AreaSeatGenerator();

    @Override
    public Building getBuildingById(UUID id) {
        Building building = buildings.get(id);
        if (building == null) throw new IllegalArgumentException("Invalid building id.");
        return building;
    }

    @Override
    public List<Building> getAllBuildings() {
        List<Building> buildings = new ArrayList<>(this.buildings.values());
        buildings.sort(Comparator.comparing(Building::getName));
        return buildings;
    }

    public void initializeDummyBuildings(Context context) {
        CSVBuildingLoader loader = new CSVBuildingLoader();
        List<Building> buildings = loader.loadBuildings(context);
        seatGenerator.generateSeats(buildings);
        buildings.forEach(building -> this.buildings.put(building.getId(), building));
    }

}
