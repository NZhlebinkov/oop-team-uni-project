package nl.tudelft.oopp.client.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.tudelft.oopp.api.HttpRequestHandler;
import nl.tudelft.oopp.api.models.ClientRequest;
import nl.tudelft.oopp.api.models.Reservation;
import nl.tudelft.oopp.api.models.UserKind;
import nl.tudelft.oopp.client.MainApp;


public class HomepageController<E> implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(HomepageController.class.getName());
    private static final String BAD_RESOURCE_ERROR = "Faulty resource input at HomePageController";

    private static final HttpRequestHandler httpRequestHandler = new HttpRequestHandler();

    @FXML
    private ListView<String> todayRes;
    @FXML
    private ListView<String> allRes;
    @FXML
    private TabPane viewReservations;
    @FXML
    private Button reserveButton;
    @FXML
    private Menu reserveMenu;
    @FXML
    private Button guestButton;
    @FXML
    private VBox guestInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!HttpRequestHandler.user.getUserKind().equals(UserKind.Guest)) {
            loadTodayReservations();
            loadAllReservations();
        } else {
            viewReservations.setVisible(false);
            reserveButton.setVisible(false);
            reserveMenu.setVisible(false);
            guestButton.setVisible(true);
            guestInfo.setVisible(true);
        }
    }

    /**
     * Handles loading all reservations for the currently logged in user to the ListView in tab today's reservations item in
     * homepage.fxml
     */
    private void loadAllReservations() {
        try {
            ClientRequest<String> userDetails = new ClientRequest<>(
                    HttpRequestHandler.user.getUsername(),
                    HttpRequestHandler.user.getUserKind(),
                    null
            );
            List<Reservation> reservationList =
                    httpRequestHandler.postList("reservations/user/current", userDetails, Reservation.class);
            if (reservationList != null) {
                for (Reservation s : reservationList) {
                    allRes.getItems().add("Room " + s.getReservable().getDetails().getName() + "in"
                            + s.getReservable().getBuilding().getName() + " reserved from "
                            + ReservationsSceneController.hourAndMinutesString(s.getTimeslot().getStartTime()) + " to "
                            + ReservationsSceneController.hourAndMinutesString(s.getTimeslot().getEndTime()) + " on "
                    );
                    allRes.getItems().add(s.getTimeslot().getStartTime().getDate() + "/"
                            + (s.getTimeslot().getStartTime().getMonth() + 1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles loading the current user's reservations for all days to the ListView in the tab all reservations in
     * homepage.fxml
     */
    private void loadTodayReservations() {
        try {
            ClientRequest<String> userDetails = new ClientRequest<>(
                    HttpRequestHandler.user.getUsername(),
                    HttpRequestHandler.user.getUserKind(),
                    null
            );
            List<Reservation> reservationList =
                    httpRequestHandler.postList("reservations/user/today", userDetails, Reservation.class);
            if (reservationList != null) {
                for (Reservation s : reservationList) {
                    todayRes.getItems().add("Room " + s.getReservable().getDetails().getName() + " in "
                            + s.getReservable().getBuilding().getName() + " reserved from "
                            + ReservationsSceneController.hourAndMinutesString(s.getTimeslot().getStartTime()) + " to "
                            + ReservationsSceneController.hourAndMinutesString(s.getTimeslot().getEndTime())
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles going to the mainScene.a
     *
     * @param event the scene from where the function was called.
     */
    public void goToMain(ActionEvent event) {
        try {
            Parent mainParent = FXMLLoader.load(getClass().getResource("/mainScene.fxml"));
            Scene mainScene = new Scene(mainParent);

            Stage primaryStage =
                    (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            primaryStage.hide();
            primaryStage.setScene(mainScene);
            primaryStage.show();

        } catch (IOException e) {
            System.out.println("IOException in HomepageController");
        }
    }

    /**
     * Handles going to the reservation page.
     *
     * @param event the scene from where the function was called.
     */
    public void goToRes(ActionEvent event) {
        try {
            Parent resParent = FXMLLoader.load(getClass().getResource("/reservations.fxml"));
            Scene resScene = new Scene(resParent);
            resScene.getStylesheets().addAll(this.getClass().getResource("/reservations.css").toExternalForm());

            Stage primaryStage = (Stage) (todayRes.getScene().getWindow());

            primaryStage.hide();
            primaryStage.setScene(resScene);
            primaryStage.show();

        } catch (IOException e) {
            System.out.println("IOException in HomepageController");
        }
    }

    /**
     * Handles going back to the login page.
     */
    public void goToLogIn() {
        try {
            MainApp.goToPage("login", "login");
        } catch (IOException e) {
            LOGGER.log(Level.FINE, BAD_RESOURCE_ERROR + ".goToRes()");
        }
    }
}
