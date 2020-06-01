package at.ac.univie.hci.findmeaseat.model.booking;

public class BookingRepositoryFactory {

    private static final BookingRepository bookingRepository = new InMemoryBookingRepository();

    public static BookingRepository getSingletonInstance() {
        return bookingRepository;
    }

}
