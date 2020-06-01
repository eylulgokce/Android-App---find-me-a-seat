package at.ac.univie.hci.findmeaseat.model.building;

import java.util.Objects;

public final class Seat {

    private final String name;
    private final Area area;

    Seat(String name, Area area) {
        this.name = name;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public Area getArea() {
        return area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(name, seat.name) &&
                Objects.equals(area, seat.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, area);
    }
}
