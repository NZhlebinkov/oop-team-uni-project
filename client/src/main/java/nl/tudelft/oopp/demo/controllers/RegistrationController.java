package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {

    /**
     * Handles going back to the Homepage.
     * @param event the event from where the function was called.
     */
    public void goToHomepage(ActionEvent event) {
        try {
            Parent homepageParent = FXMLLoader.load(getClass().getResource("/mainScene.fxml"));
            Scene homepageScene = new Scene(homepageParent);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            primaryStage.hide();
            primaryStage.setScene(homepageScene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("IOException in ReservationsController");
        }
    }

    /**
     * Handles going to the Login.
     * @param event the event from where the function was called.
     */
    public void goToLogin(MouseEvent event) {
        try {
            Parent loginParent = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Scene loginScene = new Scene(loginParent);
            loginScene.getStylesheets().addAll(this.getClass().getResource("/login.css").toExternalForm());
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            primaryStage.hide();
            primaryStage.setScene(loginScene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("IOException in ReservationsController");
        }
    }
}
