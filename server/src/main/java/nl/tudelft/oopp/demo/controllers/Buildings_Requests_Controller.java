package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.models.Building;
import nl.tudelft.oopp.demo.services.BuildingService;
import nl.tudelft.oopp.demo.services.LoggerService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/buildings")
public class Buildings_Requests_Controller {

    @Autowired
    BuildingService service;

    @GetMapping("/all")
    ResponseEntity<List<Building>> sendAllBuildings(HttpSession session) {

        LoggerService.info(Buildings_Requests_Controller.class ,
                "Request for all available buildings received. Processing ...");
        System.out.println(session);
        try {
            if(session.getAttribute("NetID") == null) throw new IllegalAccessException();
        }
        catch (IllegalAccessException exception) {
            LoggerService.error(Buildings_Requests_Controller.class ,
                    "Unauthorized attempt to view all buildings. No session found for this user.");
            return ResponseEntity.badRequest().build();
        }

        List<Building> buildings = service.getAllBuildings();

        return ResponseEntity.ok().body(buildings);
    }



}
