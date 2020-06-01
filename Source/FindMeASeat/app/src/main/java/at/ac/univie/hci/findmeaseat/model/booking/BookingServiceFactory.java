package at.ac.univie.hci.findmeaseat.model.booking;

import at.ac.univie.hci.findmeaseat.model.booking.status.SeatStatusServiceFactory;
import at.ac.univie.hci.findmeaseat.model.user.AuthenticationServiceFactory;

public class BookingServiceFactory {

    static private final BookingService bookingService = new DummyBookingService(
            AuthenticationServiceFactory.getSingletonInstance(),
            BookingRepositoryFactory.getSingletonInstance(),
            SeatStatusServiceFactory.getSingletonInstance()
    );

    public static BookingService getSingletonInstance() {
        return bookingService;
    }

}
