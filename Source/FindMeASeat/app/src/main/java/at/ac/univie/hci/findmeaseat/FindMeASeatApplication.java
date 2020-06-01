package at.ac.univie.hci.findmeaseat;

import android.app.Application;

import at.ac.univie.hci.findmeaseat.model.booking.BookingService;
import at.ac.univie.hci.findmeaseat.model.booking.BookingServiceFactory;
import at.ac.univie.hci.findmeaseat.model.booking.DummyBookingService;
import at.ac.univie.hci.findmeaseat.model.building.service.BuildingService;
import at.ac.univie.hci.findmeaseat.model.building.service.BuildingServiceFactory;
import at.ac.univie.hci.findmeaseat.model.building.service.DummyBuildingService;

public class FindMeASeatApplication extends Application {

    private final BuildingService buildingService = BuildingServiceFactory.getSingletonInstance();
    private final BookingService bookingService = BookingServiceFactory.getSingletonInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        if (buildingService instanceof DummyBuildingService) {
            ((DummyBuildingService) buildingService).initializeDummyBuildings(this);
        }
        if (bookingService instanceof DummyBookingService) {
            ((DummyBookingService) bookingService).initializeDummyBookings(buildingService.getAllBuildings());
        }
    }

}
