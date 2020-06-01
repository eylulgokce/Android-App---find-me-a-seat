package at.ac.univie.hci.findmeaseat.model.user.favorite;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import at.ac.univie.hci.findmeaseat.model.building.Building;
import at.ac.univie.hci.findmeaseat.model.user.AuthenticationService;

public class InMemoryFavoriteService implements FavoriteService {

    private final List<Favorite> favorites = new ArrayList<>();

    private final AuthenticationService authenticationService;

    InMemoryFavoriteService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void addToFavorites(Building building) {
        favorites.add(new Favorite(building, authenticationService.getAuthenticatedUser().getId()));
    }

    @Override
    public void removeFromFavorites(Building building) {
        Optional<Favorite> favorite = favorites
                .stream()
                .filter(f -> f.building.getId().equals(building.getId()) && f.userId.equals(authenticationService.getAuthenticatedUser().getId()))
                .findFirst();
        favorite.ifPresent(favorites::remove);
    }

    @Override
    public boolean isFavorite(Building building) {
        return favorites
                .stream()
                .anyMatch(f -> f.building.getId().equals(building.getId()) && f.userId.equals(authenticationService.getAuthenticatedUser().getId()));
    }

    @Override
    public List<Building> getAllFavorites() {
        return favorites
                .stream()
                .filter(f -> f.userId.equals(authenticationService.getAuthenticatedUser().getId()))
                .map(f -> f.building)
                .collect(Collectors.toList());
    }


    private static class Favorite {
        final Building building;
        final UUID userId;

        Favorite(Building building, UUID userId) {
            this.building = building;
            this.userId = userId;
        }
    }

}
