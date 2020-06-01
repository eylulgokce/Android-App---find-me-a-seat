package at.ac.univie.hci.findmeaseat.model.building;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class Building {

    private final UUID id;
    private final String name;
    private final Address address;
    private final Map<String, Area> areas;

    public Building(@NonNull String name, @NonNull Address address) {
        id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.areas = new HashMap<>();
    }

    public void addArea(String name) {
        areas.put(name, new Area(name, this));
    }

    public Area getArea(String name) {
        Area area = areas.get(name);
        if (area == null) throw new IllegalArgumentException("Area not found.");
        return area;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<Area> getAllAreas() {
        List<Area> areas = new ArrayList<>(this.areas.values());
        areas.sort(Comparator.comparing(Area::getName));
        return areas;
    }

    public int maximalSeats() {
        return getAllAreas().stream().map(area -> area.getAllSeats().size()).reduce(0, Integer::sum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return Objects.equals(id, building.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
