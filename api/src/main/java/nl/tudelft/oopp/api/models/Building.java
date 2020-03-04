package nl.tudelft.oopp.api.models;

public class Building {

    String name;
    int buildingInt;
    int capacity;

    /**
     * An example model, to showcase the usage of the API. This will be deleted in the final product.
     * @param name The building name.
     * @param buildingInt the building number.
     * @param capacity The building capacity.
     */
    public Building(String name, int buildingInt, int capacity) {
        this.name = name;
        this.buildingInt = buildingInt;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBuildingInt() {
        return buildingInt;
    }

    public void setBuildingInt(int buildingInt) {
        this.buildingInt = buildingInt;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
