package at.ac.univie.hci.findmeaseat.model.user;

public class AuthenticationServiceFactory {

    private static final AuthenticationService authenticationService = new DummyAuthenticationService();

    public static AuthenticationService getSingletonInstance() {
        return authenticationService;
    }

}
