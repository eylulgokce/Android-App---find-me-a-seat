package at.ac.univie.hci.findmeaseat.model.user;

public interface AuthenticationService {

    User getAuthenticatedUser();

    void authenticateUser(String username, String password);

}
