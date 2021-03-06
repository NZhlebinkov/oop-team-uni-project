package nl.tudelft.oopp.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Initialises a new {@link Reservable}.
 */
@Entity
@Table(name = "Reservable")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Room.class),
    @JsonSubTypes.Type(value = Bike.class)
})
public abstract class Reservable {

    /**
     * The reservable's unique Id.
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * This is a details entity that tells you information about a reservable.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details")
    private Details details;

    @ManyToOne(cascade = CascadeType.MERGE) /* This way, the building is automatically updated if a change is made to it
                                            through the reservable*/
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservable")
    private List<Reservation> reservations;

    /**
     * Initialises a new instance of {@link Reservable}.
     */
    public Reservable() {

    }

    /** Constructs a new {@link Reservable} object.
     * @param id        The unique id of the reservable.
     * @param details   The details for the new reservable.
     */
    public Reservable(Long id, Details details) {
        this.id = id;
        this.details = details;
        this.reservations = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
