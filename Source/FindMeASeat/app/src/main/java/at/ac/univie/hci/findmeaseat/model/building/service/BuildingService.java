package at.ac.univie.hci.findmeaseat.model.building.service;

import java.util.List;
import java.util.UUID;

import at.ac.univie.hci.findmeaseat.model.building.Building;

public interface BuildingService {

    Building getBuildingById(UUID id);

    List<Building> getAllBuildings();

}
