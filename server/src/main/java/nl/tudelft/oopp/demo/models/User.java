package nl.tudelft.oopp.demo.models;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Initialises a new {@link User}.
 */
@Entity
@Table(name = "user")
public class User {
    /**
     * Initialises a new {@link User}.
     */
    public User() {

    }

    /**
     * The user's netID...?
     */
    @Id
    @Column(name = "user_id")
    public Integer userId;

    /**
     * The user's name...?
     */
    @Column(name = "name")
    public String name;

    /**
     * The user's username...?
     */
    @Column(name = "username")
    public String username;

    /**
     * The user's email.
     */
    @Column(name = "email")
    public String email;

    /**
     * The user's password.
     */
    @Column(name = "password")
    public String password;

    /**
     * A collection of the user's current reservations.
     */
    @ElementCollection
    @Column(name = "reservations")
    public Collection<Reservation> reservations;
}