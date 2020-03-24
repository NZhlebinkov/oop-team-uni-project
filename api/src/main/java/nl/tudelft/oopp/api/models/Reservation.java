package nl.tudelft.oopp.api.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Initialises a new isntance of {@link Reservation}.
 */
public class Reservation {

    /**
     * The reservation's unique ID.
     */
    public Long reservationID;

    /**
     * The ID of the user who made the reservation.
     */
    public User user;

    /**
     * This is a reservation of a specific reservable.
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = Room.class, name = "room"),
        @JsonSubTypes.Type(value = Bike.class, name = "bike")
    })
    public Reservable reservable;

    /**
     * This shows the timeslot of a reservation.
     */
    public TimeSlot timeslot;

    /**
     * Initialises a new instance of {@link Reservation}.
     */
    public Reservation() {

    }

    /**
     * Initialises a new instance of {@link Reservation}.
     *
     * @param reservationID The reservation's unique ID.
     * @param user          The user holding the reservation.
     * @param reservable    The entity being reserved.
     * @param timeslot      The time during which the entity will be reserved.
     */
    public Reservation(Long reservationID, User user, Reservable reservable,
                       TimeSlot timeslot) {
        this.user = user;
        this.reservable = reservable;
        this.timeslot = timeslot;
        this.reservationID = reservationID;
    }

    /**
     * Initialises a new instance of {@link Reservation} without ID.
     *
     * @param user          The user holding the reservation.
     * @param reservable    The entity being reserved.
     * @param timeslot      The time during which the entity will be reserved.
     */
    public Reservation(User user, Reservable reservable,
                       TimeSlot timeslot) {
        this.user = user;
        this.reservable = reservable;
        this.timeslot = timeslot;
    }


    public Long getReservationID() {
        return reservationID;
    }

    public void setReservationID(Long reservationID) {
        this.reservationID = reservationID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reservable getReservable() {
        return reservable;
    }

    public void setReservable(Reservable reservable) {
        this.reservable = reservable;
    }

    public TimeSlot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(TimeSlot timeslot) {
        this.timeslot = timeslot;
    }
}
