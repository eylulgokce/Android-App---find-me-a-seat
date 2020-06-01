package at.ac.univie.hci.findmeaseat.model.user.favorite;

import java.util.List;

import at.ac.univie.hci.findmeaseat.model.building.Building;

public interface FavoriteService {

    void addToFavorites(Building building);

    void removeFromFavorites(Building building);

    boolean isFavorite(Building building);

    List<Building> getAllFavorites();

}
