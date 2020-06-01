package at.ac.univie.hci.findmeaseat.model.user.favorite;

import at.ac.univie.hci.findmeaseat.model.user.AuthenticationServiceFactory;

public class FavoriteServiceFactory {

    private static final FavoriteService favoriteService = new InMemoryFavoriteService(AuthenticationServiceFactory.getSingletonInstance());

    public static FavoriteService getSingletonInstance() {
        return favoriteService;
    }

}
