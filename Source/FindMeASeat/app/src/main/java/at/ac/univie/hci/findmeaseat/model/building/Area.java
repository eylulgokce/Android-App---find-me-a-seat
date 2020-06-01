package at.ac.univie.hci.findmeaseat.model.building;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Area {

    private final String name;
    private final Building building;
    private final Map<String, Seat> seats;

    Area(String name, Building building) {
        this.name = name;
        this.building = building;
        this.seats = new HashMap<>();
    }

    void addSeat(String name) {
        seats.put(name, new Seat(name, this));
    }

    public String getName() {
        return name;
    }

    public Building getBuilding() {
        return building;
    }

    public Collection<Seat> getAllSeats() {
        List<Seat> seats = new ArrayList<>(this.seats.values());
        seats.sort(Comparator.comparing(Seat::getName));
        return seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return Objects.equals(name, area.name) &&
                Objects.equals(building, area.building);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, building);
    }
}
