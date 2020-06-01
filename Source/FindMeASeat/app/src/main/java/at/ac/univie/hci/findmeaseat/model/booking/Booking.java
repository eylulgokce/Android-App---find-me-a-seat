package at.ac.univie.hci.findmeaseat.model.booking;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

import at.ac.univie.hci.findmeaseat.model.building.Seat;

public final class Booking {

    private final UUID id;
    private final UUID userId;
    private final Seat seat;
    private final LocalDateTime start;
    private final LocalDateTime end;


    public Booking(@NonNull UUID userId, @NonNull Seat seat, @NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.seat = seat;
        this.start = start;
        this.end = end;
    }

    public UUID getId() {
        return id;
    }

    UUID getUserId() {
        return userId;
    }

    public Seat getSeat() {
        return seat;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

}
