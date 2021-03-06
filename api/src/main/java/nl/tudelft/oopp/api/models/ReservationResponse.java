package nl.tudelft.oopp.api.models;

import java.util.List;

public class ReservationResponse {

    private List<Reservation> reservationList;

    public ReservationResponse(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public ReservationResponse(){}

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}

