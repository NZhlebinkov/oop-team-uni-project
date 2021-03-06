package nl.tudelft.oopp.client.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import nl.tudelft.oopp.api.HttpRequestHandler;
import nl.tudelft.oopp.api.models.Building;
import nl.tudelft.oopp.api.models.BuildingBasicInfo;
import nl.tudelft.oopp.api.models.Details;
import nl.tudelft.oopp.api.models.ServerResponseAlert;
import nl.tudelft.oopp.api.models.UserKind;
import nl.tudelft.oopp.client.MainApp;


public class ReservationsSceneController implements Initializable {

    private static final HttpRequestHandler httpRequestHandler = new HttpRequestHandler();

    public static final int MAX_DAYS_IN_ADVANCE = 14;
    public static final int RESPONSE_TIMEOUT = 5;

    @FXML
    ListView<Node> buildingsList;

    @FXML
    ChoiceBox<LocalDate> datesList;

    @FXML
    TextField buildingSearchField;

    @FXML
    TabPane roomsListWrapper;

    @FXML
    Tab roomsListTab;


    /**
     * Adds GUI that can only be generated at the moment of loading the page.
     * The parameter descriptions are from the official fxml javadoc.
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     *              the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateDatesChoiceBox();

        populateBuildingsScrollBox();

        roomsListWrapper.setVisible(false);
    }

    /**
     * Generates dates for the next 14(@MAX_DAYS_IN_ADVANCE) days and adds them to the GUI
     * in the dropdown menu.
     */
    private void populateDatesChoiceBox() {
        LocalDate date = LocalDate.now();


        datesList.setConverter(generateDateConverter());
        datesList.setValue(date);
        datesList.getItems().add(date);

        for (int i = 1; i != MAX_DAYS_IN_ADVANCE; i++) {
            date = date.plusDays(1);
            datesList.getItems().add(date);
        }
    }

    /**
     * Generates boxes for each building and adds them to the GUI.
     */
    private void populateBuildingsScrollBox() {
        List<BuildingBasicInfo> buildingBasicInfos = httpRequestHandler.getList("buildings/user/all/information",
                BuildingBasicInfo.class);

        DropShadow dropShadow = new DropShadow(BlurType.ONE_PASS_BOX, new Color(0,0,0,0.1), 2,4,2, 2);
        buildingSearchField.setEffect(dropShadow);

        List<Node> listOfEntries = new ArrayList<Node>();
        for (BuildingBasicInfo building : buildingBasicInfos) {
            VBox buildingEntry = new VBox();
            buildingEntry.getStyleClass().add("buildingEntry");
            Label buildingName = new Label(building.getNumber() + "," + building.getDetails().getName());
            buildingName.getStyleClass().add("buildingName");
            Label buildingOpeningTime = new Label(hourAndMinutesString(building.getOpeningHours().getStartTime())
                + " - " + hourAndMinutesString(building.getOpeningHours().getEndTime()));
            buildingOpeningTime.getStyleClass().add("buildingOpeningTime");

            buildingEntry.getChildren().add(buildingName);
            buildingEntry.getChildren().add(buildingOpeningTime);
            buildingEntry.setPrefHeight(60);
            buildingEntry.prefWidthProperty().bind(buildingsList.widthProperty().subtract(50));

            buildingEntry.setEffect(dropShadow);

            listOfEntries.add(buildingEntry);

            EventHandler<MouseEvent> mouseEventEventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/roomsList.fxml"));
                        RoomsListController controller = new RoomsListController();
                        loader.setController(controller);
                        VBox tabContent = loader.load();
                        roomsListWrapper.setVisible(true);
                        roomsListTab.setContent(tabContent);

                        controller.initialize(building);
                    } catch (IOException e) {
                        System.out.println("File Not Found");
                    }
                }
            };
            buildingEntry.setOnMouseClicked(mouseEventEventHandler);
        }
        if (listOfEntries.size() != 0) {
            ObservableList<Node> observableListOfEntries = FXCollections.observableArrayList(listOfEntries);
            buildingsList.setItems(observableListOfEntries);
        }
    }

    /**
     * Gets the hour and minutes from a {@link Timestamp} in the format (H)H:MM.
     * @param timestamp The timestamp used.
     * @return a String of format (H)H:MM (e.g. 9:13 or 12:00).
     */
    public static String hourAndMinutesString(Timestamp timestamp) {
        String result = timestamp.getHours() + ":";
        if (timestamp.getMinutes() < 10) {
            return result + "0" + timestamp.getMinutes();
        } else {
            return result + timestamp.getMinutes();
        }
    }

    private StringConverter<LocalDate> generateDateConverter() {
        return new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                String result = date.getDayOfMonth() + "/"
                    + date.getMonth().getValue()
                    + "/" + date.getYear() + " - "
                    + date.getDayOfWeek().name().substring(0,1)
                    + date.getDayOfWeek().name().substring(1,3).toLowerCase();
                if (date.equals(LocalDate.now())) {
                    return result + " (Today)";
                } else {
                    return result;
                }
            }

            @Override
            public LocalDate fromString(String dateString) {
                String[] splitString = dateString.split("/");
                return LocalDate.of(Integer.parseInt(splitString[0]),
                    Integer.parseInt(splitString[1]),
                    Integer.parseInt(splitString[2].substring(0,3)));
            }
        };
    }

    /**
     * Handles going back to the Homepage based on type of user.
     */
    public void goToHomepage() {
        if (HttpRequestHandler.user.getUserKind().equals(UserKind.Admin)) {
            goToAdmin();
        } else {
            goToHome();
        }
    }

    /**
     * Handles going to the admin homepage.
     */
    public void goToAdmin() {
        try {
            MainApp.goToPage("admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles going to the user homepage.
     */
    public void goToHome() {
        try {
            MainApp.goToPage("homepage");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
