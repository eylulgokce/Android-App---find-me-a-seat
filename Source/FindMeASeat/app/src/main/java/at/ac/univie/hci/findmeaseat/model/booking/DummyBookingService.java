package at.ac.univie.hci.findmeaseat.model.booking;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import at.ac.univie.hci.findmeaseat.model.booking.status.SeatStatusService;
import at.ac.univie.hci.findmeaseat.model.building.Building;
import at.ac.univie.hci.findmeaseat.model.building.Seat;
import at.ac.univie.hci.findmeaseat.model.user.AuthenticationService;

import static at.ac.univie.hci.findmeaseat.model.booking.status.SeatStatusService.SeatStatus.OCCUPIED;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

public final class DummyBookingService implements BookingService {

    private final AuthenticationService authenticationService;
    private final BookingRepository bookingRepository;
    private final SeatStatusService seatStatusService;

    DummyBookingService(AuthenticationService authenticationService, BookingRepository bookingRepository, SeatStatusService seatStatusService) {
        this.authenticationService = authenticationService;
        this.bookingRepository = bookingRepository;
        this.seatStatusService = seatStatusService;
    }

    @Override
    public Booking getBookingById(UUID id) {
        return bookingRepository.findById(id);
    }

    @Override
    public void cancelBooking(Booking booking) {
        bookingRepository.remove(booking);
    }

    @Override
    public void bookSeat(Seat seat, Period period) {
        if (seatStatusService.getStatus(seat, period).equals(OCCUPIED)) throw new IllegalStateException("Can't book occupied seat");
        Booking booking = new Booking(authenticationService.getAuthenticatedUser().getId(), seat, period.getStart(), period.getEnd());
        bookingRepository.save(booking);
    }

    @Override
    public void bookAnySeat(Building building, Period period) {
        List<Seat> freeSeats = seatStatusService.getFreeSeats(building, period);
        if (freeSeats.isEmpty()) throw new IllegalArgumentException("No seats are available");
        int randomSeatIndex = Math.max(ThreadLocalRandom.current().nextInt(0, freeSeats.size() + 1) - 1, 0);
        Seat selectedSeat = freeSeats.get(randomSeatIndex);
        bookSeat(selectedSeat, period);
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = bookingRepository.findByUser(authenticationService.getAuthenticatedUser().getId());
        return sortBookings(bookings);
    }

    @Override
    public List<Booking> getCurrentBookings() {
        List<Booking> bookings = bookingRepository.findByUser(authenticationService.getAuthenticatedUser().getId())
                .stream()
                .filter(booking -> booking.getEnd().isAfter(now()) || booking.getStart().isAfter(now()))
                .collect(Collectors.toList());
        return sortBookings(bookings);
    }

    public void initializeDummyBookings(List<Building> buildings) {
        Period period = new Period(now().minusMinutes(5), now().plusMinutes(55));
        for (Building building : buildings) {
            List<Seat> freeSeats = seatStatusService.getFreeSeats(building, period);
            int bookingCount = ThreadLocalRandom.current().nextInt(0, freeSeats.size() + 1);
            Set<Integer> randomSeatIndices = new HashSet<>();
            for (int i = 0; i < bookingCount * 3; ++i) {
                int randomIndex = Math.max(ThreadLocalRandom.current().nextInt(0, freeSeats.size() + 1) - 1, 0);
                randomSeatIndices.add(randomIndex);
            }
            for (int index : randomSeatIndices) {
                Seat seat = freeSeats.get(index);
                Booking booking = new Booking(randomUUID(), seat, period.getStart(), period.getEnd());
                Booking bookingYesterday = new Booking(randomUUID(), seat, period.getStart().minusDays(1), period.getEnd().minusDays(1));
                bookingRepository.save(booking);
                bookingRepository.save(bookingYesterday);
                if (index % 2 == 0) {
                    Booking bookingTomorrow = new Booking(randomUUID(), seat, period.getStart().plusDays(1), period.getEnd().plusDays(1));
                    bookingRepository.save(bookingTomorrow);
                }
            }
        }
    }

    private List<Booking> sortBookings(List<Booking> bookings) {
        bookings.sort(Comparator.comparing(Booking::getStart));
        return bookings;
    }

}
