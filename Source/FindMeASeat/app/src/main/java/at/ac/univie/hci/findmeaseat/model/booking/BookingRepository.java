package at.ac.univie.hci.findmeaseat.model.booking;

import java.util.List;
import java.util.UUID;

import at.ac.univie.hci.findmeaseat.model.building.Seat;

public interface BookingRepository {

    void save(Booking booking);

    void remove(Booking booking);

    Booking findById(UUID id);

    List<Booking> findBySeat(Seat seat, Period period);

    List<Booking> findByUser(UUID userId);

}
