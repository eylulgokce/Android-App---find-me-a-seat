package at.ac.univie.hci.findmeaseat.model.booking.status;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import at.ac.univie.hci.findmeaseat.model.booking.Booking;
import at.ac.univie.hci.findmeaseat.model.booking.BookingRepository;
import at.ac.univie.hci.findmeaseat.model.booking.Period;
import at.ac.univie.hci.findmeaseat.model.building.Area;
import at.ac.univie.hci.findmeaseat.model.building.Building;
import at.ac.univie.hci.findmeaseat.model.building.Seat;

public class DefaultSeatStatusService implements SeatStatusService {

    private final BookingRepository bookingRepository;

    DefaultSeatStatusService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public SeatStatus getStatus(Seat seat, Period period) {
        List<Booking> bookings = bookingRepository.findBySeat(seat, period);
        if (bookings.isEmpty()) {
            return SeatStatus.FREE;
        } else {
            return SeatStatus.OCCUPIED;
        }
    }

    @Override
    public List<Seat> getFreeSeats(Building building, Period period) {
        return building.getAllAreas()
                .stream()
                .map(area -> getFreeSeats(area, period))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Seat> getFreeSeats(Area area, Period period) {
        return area.getAllSeats()
                .stream()
                .filter(seat -> getStatus(seat, period).equals(SeatStatus.FREE))
                .collect(Collectors.toList());
    }

}
