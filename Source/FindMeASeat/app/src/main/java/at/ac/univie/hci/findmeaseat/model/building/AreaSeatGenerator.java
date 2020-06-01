package at.ac.univie.hci.findmeaseat.model.building;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Random;

public class AreaSeatGenerator {

    private final Random random = new SecureRandom();

    public void generateSeats(Collection<Building> buildings) {
        buildings.forEach(this::generateSeats);
    }

    private void generateSeats(Building building) {
        int areaCount = random.nextInt(8 - 1) + 1;

        for (int i = 1; i <= areaCount; ++i) building.addArea(i + "." + " " + "Stock");

        int seatNumber = 1;
        char seatPrefix = 'A';
        for (int floor = 1; floor <= areaCount; ++floor) {
            if (floor > 1)
                ++seatPrefix;
            if (seatNumber == 11) {
                seatNumber = 0;
            }
            for (; seatNumber <= 10; ++seatNumber) {
                String finalS = String.valueOf(seatPrefix);
                int finalSeatNumber = seatNumber;
                Collection<Area> areas = building.getAllAreas();
                areas.forEach(area -> area.addSeat(finalS + finalSeatNumber));
            }
        }
    }

}
