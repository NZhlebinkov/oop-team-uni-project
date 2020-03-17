package nl.tudelft.oopp.api.models;

import nl.tudelft.oopp.api.HttpRequestHandler;
import nl.tudelft.oopp.api.models.RegistrationDetails;

/**
 * Initialises a new {@link User}.
 */
public class User {

    /**
     * The user's ID.
     */
    public Long id;

    /**
     * The user's email.
     */
    public String email;

    /**
     * The user's username.
     */
    public String username;

    /**
     * The user's password.
     */
    public String password;
    /**
     * A collection of the user's current reservations.
     */
    public Details details;

    /**
     * Initialises a new {@link User}.
     */
    public User() {

    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * Creates a user from a provided registration details object.
     *
     * @param registrationDetails The registration details from which to create a new user
     */
    public User(RegistrationDetails registrationDetails) {
        this.details =
                HttpRequestHandler.convertModel(registrationDetails.getDetails(), Details.class);
        this.email = registrationDetails.getEmail();
        this.username = registrationDetails.getusername();
        this.password = registrationDetails.getPassword();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
}
