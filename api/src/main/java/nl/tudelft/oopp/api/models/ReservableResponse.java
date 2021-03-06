package nl.tudelft.oopp.api.models;

import java.util.List;

public class ReservableResponse {

    private List<Reservable> reservableList;

    public ReservableResponse() {}

    public ReservableResponse(List<Reservable> reservableList) {
        this.reservableList = reservableList;
    }

    public List<Reservable> getReservableList() {
        return reservableList;
    }

    public void setReservableList(List<Reservable> reservableList) {
        this.reservableList = reservableList;
    }
}
