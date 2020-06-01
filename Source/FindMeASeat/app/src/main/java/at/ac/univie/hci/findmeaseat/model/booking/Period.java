package at.ac.univie.hci.findmeaseat.model.booking;

import java.time.LocalDateTime;

public class Period {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Period(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
