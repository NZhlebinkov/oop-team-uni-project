package nl.tudelft.oopp.client.controllers;

import static nl.tudelft.oopp.client.controllers.ReservationsSceneController.RESPONSE_TIMEOUT;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import nl.tudelft.oopp.api.HttpRequestHandler;
import nl.tudelft.oopp.api.models.Building;
import nl.tudelft.oopp.api.models.BuildingBasicInfo;
import nl.tudelft.oopp.api.models.Reservable;
import nl.tudelft.oopp.api.models.ReservableResponse;
import nl.tudelft.oopp.api.models.Room;
import nl.tudelft.oopp.api.models.RoomResponse;
import nl.tudelft.oopp.api.models.UserKind;

public class RoomsListController implements Initializable {

    private static final HttpRequestHandler httpRequestHandler = new HttpRequestHandler();

    @FXML
    private ListView<RoomEntryComponent> roomEntriesContainer;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * This will get all the rooms of the selected {@link Building} and create GUI for them, as well as the
     * initial tab layout.
     *
     * @param buildingBasicInfo The {@link Building} from which to take the rooms (and opening hours)
     */
    public void initialize(BuildingBasicInfo buildingBasicInfo) {
        startTimeLabel.setText(ReservationsSceneController
            .hourAndMinutesString(buildingBasicInfo.getOpeningHours().getStartTime()));
        endTimeLabel.setText(ReservationsSceneController
            .hourAndMinutesString(buildingBasicInfo.getOpeningHours().getEndTime()));
        ReservableResponse reservableResponse = httpRequestHandler.get(
                "reservables/user/all/room/building/" + buildingBasicInfo.getNumber(), ReservableResponse.class);
        generateRooms(reservableResponse.getReservableList());
    }

    /**
     * Generate the rooms of the building based on the user's access rights.
     * @param reservables The response from the server, containing all of the building's rooms.
     */
    private void generateRooms(List<Reservable> reservables) {
        ObservableList<RoomEntryComponent> roomEntries = FXCollections.observableArrayList();
        if (HttpRequestHandler.user.getUserKind() == UserKind.Student
            || HttpRequestHandler.user.getUserKind() == UserKind.Guest) {
            for (Reservable reservable:reservables) {
                if (reservable instanceof Room) {
                    Room myRoom = (Room) reservable;
                    if (!myRoom.isForEmployee()) {
                        RoomEntryComponent roomEntry = new RoomEntryComponent(myRoom);
                        roomEntries.add(roomEntry);
                    }
                }
            }
        } else {
            for (Reservable reservable : reservables) {
                if (reservable instanceof Room) {
                    Room myRoom = (Room) reservable;
                    RoomEntryComponent roomEntry = new RoomEntryComponent(myRoom);
                    roomEntries.add(roomEntry);
                }
            }
        }
        roomEntriesContainer.setItems(roomEntries);
    }
}
