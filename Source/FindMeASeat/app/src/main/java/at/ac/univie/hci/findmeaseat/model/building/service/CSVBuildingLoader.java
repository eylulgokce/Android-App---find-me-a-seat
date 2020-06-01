package at.ac.univie.hci.findmeaseat.model.building.service;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import at.ac.univie.hci.findmeaseat.R;
import at.ac.univie.hci.findmeaseat.model.building.Address;
import at.ac.univie.hci.findmeaseat.model.building.Building;

class CSVBuildingLoader {

    List<Building> loadBuildings(Context context) {
        final List<Building> buildings = new ArrayList<>();
        InputStream is = context.getResources().openRawResource(R.raw.buildings);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] separator = line.split(";");
                String buildingName = separator[0];
                String street = separator[1];
                String city = separator[2];
                String zibCode = separator[3];
                buildings.add(new Building(buildingName, new Address(street, city, zibCode)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buildings;
    }
}
