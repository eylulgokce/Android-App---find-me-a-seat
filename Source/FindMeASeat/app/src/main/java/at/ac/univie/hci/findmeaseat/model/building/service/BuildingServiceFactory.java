package at.ac.univie.hci.findmeaseat.model.building.service;

public class BuildingServiceFactory {

    private static final BuildingService buildingService = new DummyBuildingService();

    public static BuildingService getSingletonInstance() {
        return buildingService;
    }

}
