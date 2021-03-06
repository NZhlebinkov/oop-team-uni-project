package nl.tudelft.oopp.api.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class Details {

    /**
     * This is the id of the detail that is a randomly generated value.
     */
    private Long id;

    /**
     * This is the name of the detail.
     */
    private String name;

    /**
     * This is the description of the detail.
     */
    private String description;

    /**
     * This is the image of the detail.
     */
    private String image;

    /**
     * Initialises a new instance of a {@link Details}.
     */
    public Details() {

    }

    /**
     * Initialises a new instance of a {@link Details}.
     *
     * @param id          The id of the Details.
     * @param name        The name of the object.
     * @param description A description of the object.
     * @param image       An image of the object.
     */
    @JsonCreator
    public Details(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("image") String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    /**
     * Initialises a new instance of a {@link Details}.
     * This constructor is used once in ReservationSceneController.
     *
     * @param name        The name of the object.
     * @param description A description of the object.
     * @param image       An image of the object.
     */
    public Details(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Details details = (Details) o;
        return Objects.equals(getId(), details.getId())
               && Objects.equals(getName(), details.getName())
               && Objects.equals(getDescription(), details.getDescription())
               && Objects.equals(getImage(), details.getImage());
    }

}
