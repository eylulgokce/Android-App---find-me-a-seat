package at.ac.univie.hci.findmeaseat.model.booking;

import java.util.List;
import java.util.UUID;

import at.ac.univie.hci.findmeaseat.model.building.Building;
import at.ac.univie.hci.findmeaseat.model.building.Seat;

public interface BookingService {

    Booking getBookingById(UUID id);

    void cancelBooking(Booking booking);

    void bookSeat(Seat seat, Period period);

    void bookAnySeat(Building building, Period period);

    List<Booking> getAllBookings();

    List<Booking> getCurrentBookings();

}
