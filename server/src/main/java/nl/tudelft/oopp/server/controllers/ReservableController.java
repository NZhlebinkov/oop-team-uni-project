package nl.tudelft.oopp.server.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.management.InstanceAlreadyExistsException;
import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;
import nl.tudelft.oopp.api.HttpRequestHandler;
import nl.tudelft.oopp.api.models.ClientRequest;
import nl.tudelft.oopp.api.models.NewReservableInfo;
import nl.tudelft.oopp.api.models.ReservableResponse;
import nl.tudelft.oopp.api.models.RoomResponse;
import nl.tudelft.oopp.api.models.ServerResponseAlert;
import nl.tudelft.oopp.server.models.Reservable;
import nl.tudelft.oopp.server.models.Room;
import nl.tudelft.oopp.server.services.AuthorizationService;
import nl.tudelft.oopp.server.services.LoggerService;
import nl.tudelft.oopp.server.services.ReservableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reservables")
public class ReservableController {

    private Logger logger = LoggerFactory.getLogger(ReservableController.class);

    private static final String NOT_ADMIN =
        "Unauthorized request. The requesting user is not an administrator.";

    private static final String NO_USER_FOUND =
        "Authentication for user failed. No administrator with that name found.";

    /**
     * Importing the methods from the service class.
     */
    final ReservableService reservableService;
    final AuthorizationService authorizationService;

    public ReservableController(ReservableService reservableService,
                                AuthorizationService authorizationService) {
        this.reservableService = reservableService;
        this.authorizationService = authorizationService;
    }

    /**
     * Gets all rooms from the database.
     * @return A {@link RoomResponse} containing a list of all rooms.
     */
    @GetMapping("/all/rooms")
    public ResponseEntity<RoomResponse> getAllReservables() {
        LoggerService.info(ReservationsController.class,
                "Received request for all reservables");


        List<nl.tudelft.oopp.api.models.Room> responseList = new ArrayList<>();
        for (Reservable responseReservable: reservableService.getAllReservables()) {
            if (responseReservable instanceof Room) {
                LoggerService.info(ReservableController.class, (HttpRequestHandler.convertModel(
                        responseReservable, nl.tudelft.oopp.api.models.Room.class
                ).details.name));
                responseList.add(HttpRequestHandler.convertModel(
                        responseReservable, nl.tudelft.oopp.api.models.Room.class
                ));
            }

        }
        return ResponseEntity.ok(new RoomResponse(responseList));
    }

    /** Receives a GET request for all rooms or bikes of a particular building. First authenticates
     *      the user by his username using the {@link AuthorizationService} bean and then uses the
     *      {@link nl.tudelft.oopp.server.services.ReservableService} to fetch all rooms or bikes
     *      of the building with the provided id as a request parameter. Sends the list wrapped in
     *      a {@link ResponseEntity} object.
     *
     * @param request   The client request containing the username to be authenticated.
     * @param id        The id of the building to fetch the rooms of.
     * @param type      The type of reservable to retrieve - rooms or bikes.
     * @return          A {@link ResponseEntity} object containing a list of the building's rooms.
     */
    @GetMapping("/{role:(?:user|admin)}/all/{type}/building")
    public ResponseEntity<RoomResponse> getAllReservablesOfBuilding(
        @RequestBody ClientRequest<String> request,
        @RequestParam Long id,
        @PathVariable String type) {

        logger.info("Received GET requests for all " + type
            + " of building " + id + ". Processing ...");

        try {
            authorizationService.authenticateUser(request.getUsername());
        } catch (AuthenticationException e) {
            logger.error(NO_USER_FOUND);
            return ResponseEntity.badRequest().build();
        }

        logger.info("Fetching all + " + type + " of building " + id + " ...");

        try {
            List<Reservable> reservablesToSend =
                reservableService.getAllReservablesForBuilding(id, type);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }

        logger.info("Sending the " + type + " of building " + id +  " ...");
        return null;
    }


    /** Endpoint that receives a PUT request for adding a new reservable to a building whose
     *      id is provided as a request parameter and the type of reservable as a path variable.
     *      The new reservable is encapsulated in a {@link ClientRequest} object.
     * @param request   The {@link ClientRequest} object containing the new reservable to add.
     * @param id        The id of the building to add the new reservable to.
     * @param type      The type of reservable to add - room or bike.
     * @return          A {@link ResponseEntity} object indicating whether the operation was
     *                  successful.
     */
    @PutMapping("/insert/{type}")
    public ResponseEntity<ServerResponseAlert> addNewReservable(
        @RequestBody ClientRequest<Reservable> request,
        @RequestParam Long id,
        @PathVariable String type) {

        logger.info("Received PUT request for adding a new " + type + " to building " + id
            + ". Processing ...");

        try {
            authorizationService.authenticateUser(request.getUsername());
        } catch (AuthenticationException e) {
            logger.error(NO_USER_FOUND);
            return ResponseEntity.badRequest().build();
        }

        logger.info("Adding new " + type + " to building " + id);

        try {
            reservableService.addReservable(request.getBody(), id);
        } catch (EntityNotFoundException e) {
            logger.error("Building " + id + " not found!");
            return ResponseEntity.badRequest().build();
        } catch (InstanceAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new ServerResponseAlert(
               "Reservable with that name already exists",
                "ERROR"
            ));
        }

        logger.info("Adding of " + type + " to building " + id + " successful. ");
        return ResponseEntity.ok(new ServerResponseAlert("Adding of " + type
            + "successful.", "SUCCESS"));
    }

    /**
     * Updates a room in the database.
     * @param newReservable the Reservable to replace the old reservable with.
     * @param id The id of the reservable that should be updated.
     */
    @PutMapping("models/Reservable/{id}")
    public void update(@RequestBody Reservable newReservable, @PathVariable Long id) {
        reservableService.updateReservable(id, newReservable);
    }
    
    /**
     * Deletes a specific reservable from the database.
     *
     * @param id the ID of the reservable to delete.
     */
    @DeleteMapping("/Reservable/{id}")
    public void delete(@PathVariable Long id) {
        reservableService.deleteReservable(id);
    }

}
