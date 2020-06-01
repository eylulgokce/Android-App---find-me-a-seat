package at.ac.univie.hci.findmeaseat.model.user;

import java.util.UUID;

public final class User {

    private final UUID id;
    private final String username;
    private final String password;
    private final String email;
    private final String matriculationNumber;
    private final String firstName;
    private final String lastName;

    User(String username, String password, String email, String matriculationNumber, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.matriculationNumber = matriculationNumber;
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMatriculationNumber() {
        return matriculationNumber;
    }

    public String getEmail() {
        return email;
    }
}
