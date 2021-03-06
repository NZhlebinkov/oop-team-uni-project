package nl.tudelft.oopp.client.controllers;

import com.sun.javafx.binding.Logging;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import nl.tudelft.oopp.api.HttpRequestHandler;
import nl.tudelft.oopp.api.models.ClientRequest;
import nl.tudelft.oopp.api.models.Reservation;
import nl.tudelft.oopp.api.models.ReservationResponse;
import nl.tudelft.oopp.api.models.UserKind;
import nl.tudelft.oopp.client.MainApp;


public class AdminController implements Initializable {

    private static final HttpRequestHandler httpRequestHandler = new HttpRequestHandler();
    private static final Logger LOGGER = Logger.getLogger(AdminController.class.getName());
    private static final String BAD_RESOURCE_ERROR = "Faulty resource input at AdminController";

    @FXML
    private ListView<String> todayRes;
    @FXML
    private ListView<String> allRes;
    @FXML
    private ListView<String> futureRes;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HomepageController.loadReservations(todayRes, "admin/today");
        HomepageController.loadReservations(futureRes, "admin/current");
        HomepageController.loadReservations(allRes, "admin/all");
    }

    /**
     * Handles going to the add buildings page for the admin.
     */
    public void goToAddBuildings() {
        try {
            MainApp.goToPage("admin-addBuilding");
        }  catch (IOException e) {
            LOGGER.log(Level.FINE, BAD_RESOURCE_ERROR + ".goToAddBuildings()");
        }
    }

    /**
     * Handles going to the delete buildings page for the admin.
     */
    public void goToDeleteBuildings() {
        try {
            MainApp.goToPage("admin-deleteBuilding");
        }  catch (IOException e) {
            LOGGER.log(Level.FINE, BAD_RESOURCE_ERROR + ".goToDeleteBuildings()");
        }
    }

    /**
     * Handles going to the view buildings page for the admin.
     */
    public void goToBuildings() {
        try {
            MainApp.goToPage("admin-viewBuilding");
        }  catch (IOException e) {
            LOGGER.log(Level.FINE, BAD_RESOURCE_ERROR + ".goToBuildings()");
        }
    }

    /**
     * Handles going to the add rooms page for the admin.
     */
    public void goToAddRooms() {
        try {
            MainApp.goToPage("admin-addRoom");
        } catch (IOException e) {
            LOGGER.log(Level.FINE, BAD_RESOURCE_ERROR + ".goToAddRooms()");
        }
    }

    /**
     * Handles going to the delete room page for the admin.
     */
    public void goToDeleteRooms() {
        try {
            MainApp.goToPage("admin-deleteRoom");
        }  catch (IOException e) {
            LOGGER.log(Level.FINE, BAD_RESOURCE_ERROR + ".goToDeleteRooms()");
        }
    }

    /**
     * Handles going to the page for adding reservations.
     */
    public void goToRes() {
        try {
            MainApp.goToPage("reservations");
        } catch (IOException e) {
            LOGGER.log(Level.FINE, BAD_RESOURCE_ERROR + ".goToRes()");
        }
    }

    /**
     * Handles going back to the login page.
     */
    public void goToLogIn() {
        try {
            MainApp.goToPage("login");
        } catch (IOException e) {
            LOGGER.log(Level.FINE, BAD_RESOURCE_ERROR + ".goToLogIn()");
        }
    }

    /**
     * Handles going to the copyrights.
     */
    public void goToCopyrights() {
        try {
            MainApp.goToPage("admin-copyrights");
        } catch (IOException e) {
            LOGGER.log(Level.FINE, BAD_RESOURCE_ERROR + ".goToCopyrights()");
        }
    }
}
