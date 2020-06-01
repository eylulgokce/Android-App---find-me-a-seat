package at.ac.univie.hci.findmeaseat.model.booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import at.ac.univie.hci.findmeaseat.model.building.Seat;

public class InMemoryBookingRepository implements BookingRepository {

    private final List<Booking> bookings = new ArrayList<>();
    private final Map<String, List<Booking>> seatToBookings = new HashMap<>();

    @Override
    public void save(Booking booking) {
        bookings.add(booking);
        String seatId = getUniqueSeatId(booking.getSeat());
        if (!seatToBookings.containsKey(seatId)) {
            seatToBookings.put(seatId, new ArrayList<>());
        }
        Objects.requireNonNull(seatToBookings.get(seatId)).add(booking);
    }

    @Override
    public void remove(Booking booking) {
        bookings.remove(booking);
        String seatId = getUniqueSeatId(booking.getSeat());
        Objects.requireNonNull(seatToBookings.get(seatId)).remove(booking);
    }

    @Override
    public Booking findById(UUID id) {
        Optional<Booking> booking = bookings
                .stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
        if (booking.isPresent()) {
            return booking.get();
        }
        throw new IllegalArgumentException("Booking could not be found.");
    }

    @Override
    public List<Booking> findBySeat(Seat seat, Period period) {
        String seatId = getUniqueSeatId(seat);
        if (!seatToBookings.containsKey(seatId)) {
            seatToBookings.put(seatId, new ArrayList<>());
        }
        return Objects.requireNonNull(seatToBookings.get(seatId))
                .stream()
                .filter(b -> !(b.getEnd().isBefore(period.getStart())) && !(b.getStart().isAfter(period.getEnd())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findByUser(UUID userId) {
        return bookings
                .stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    private String getUniqueSeatId(Seat seat) {
        return seat.getArea().getBuilding().getId().toString() + "-" + seat.getArea().getName() + "-" + seat.getName();
    }

}
