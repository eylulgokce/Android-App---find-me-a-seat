package at.ac.univie.hci.findmeaseat.model.booking.status;

import at.ac.univie.hci.findmeaseat.model.booking.BookingRepositoryFactory;

public class SeatStatusServiceFactory {

    private static final SeatStatusService seatStatusService = new DefaultSeatStatusService(BookingRepositoryFactory.getSingletonInstance());

    public static SeatStatusService getSingletonInstance() {
        return seatStatusService;
    }

}
