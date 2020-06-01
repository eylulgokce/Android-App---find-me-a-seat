package at.ac.univie.hci.findmeaseat.model.booking.status;

import java.util.List;

import at.ac.univie.hci.findmeaseat.model.booking.Period;
import at.ac.univie.hci.findmeaseat.model.building.Area;
import at.ac.univie.hci.findmeaseat.model.building.Building;
import at.ac.univie.hci.findmeaseat.model.building.Seat;

public interface SeatStatusService {

    SeatStatus getStatus(Seat seat, Period period);

    List<Seat> getFreeSeats(Building building, Period period);

    List<Seat> getFreeSeats(Area area, Period period);

    enum SeatStatus {
        FREE, OCCUPIED
    }

}
