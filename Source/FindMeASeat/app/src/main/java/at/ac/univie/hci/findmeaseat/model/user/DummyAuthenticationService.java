package at.ac.univie.hci.findmeaseat.model.user;

import java.util.HashMap;
import java.util.Map;

public class DummyAuthenticationService implements AuthenticationService {

    private User authenticatedUser;

    private final Map<String, User> users = new HashMap<>();

    DummyAuthenticationService() {
        users.put("uni_administrator", new User("uni_administrator", "admin", "admin@findmeaseat.com", "23456", "admin", "admin"));
        users.put("student1", new User("student1", "student", "student1@findmeaseat.com", "23454", "student", "student"));
        users.put("student2", new User("student2", "student", "astudent2@findmeaseat.com", "23457", "student", "student"));
        users.put("student3", new User("student3", "student", "student3@findmeaseat.com", "234543", "student", "student"));
    }

    @Override
    public User getAuthenticatedUser() {
        if (authenticatedUser == null) throw new IllegalStateException("No user is currently authenticated.");
        return authenticatedUser;
    }

    @Override
    public void authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user == null || !user.getPassword().equals(password)) throw new IllegalArgumentException("Authentication failed.");
        authenticatedUser = user;
    }

}
