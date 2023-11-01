package com.example.demo.controller;

import com.example.demo.tools.JenaEngine;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@RestController
@RequestMapping("/eventcontroller")
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {


    @GetMapping("/getEventByLabel/{label}")
    public ResponseEntity<Object> getEventByLabel(@PathVariable String label) {
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?event ?property ?value\n" +
                "WHERE {\n" +
                "  ?event a ns:Event.\n" +
                "  ?event ns:label \"" + label + "\".\n" +  // Use double quotes for the label
                "  ?event ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a JSON object to hold the event properties
        JSONObject eventObject = new JSONObject();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            String event = solution.get("event").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Add property to the event object
            eventObject.put(propertyName, propertyValue);
        }

        // Check if the event object is empty
        if (eventObject.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            // Return the event as a JSON object
            return ResponseEntity.ok(eventObject.toString());
        }
    }
    @GetMapping("/getAllEvents")
    public String getAllEvents() {
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?event ?property ?value\n" +
                "WHERE {\n" +
                "  ?event a ns:Event.\n" +
                "  ?event ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a list to hold JSON objects
        List<JSONObject> jsonObjects = new ArrayList<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            // Create a JSON object for each event
            JSONObject eventObject = new JSONObject();

            String event = solution.get("event").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Extract just the date and time part without the extra information
            if (propertyValue.contains("^^http://www.w3.org/2001/XMLSchema#dateTime")) {
                propertyValue = propertyValue.split("\\^\\^")[0];
            }

            // Add property to the event object
            eventObject.put(propertyName, propertyValue);

            // Check if the event object already exists in the list
            boolean eventExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(event)) {
                    jsonObject.getJSONObject(event).put(propertyName, propertyValue);
                    eventExists = true;
                    break;
                }
            }

            if (!eventExists) {
                // Create a new JSON object for the event and add it to the list
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(event, eventObject);
                jsonObjects.add(jsonObject);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }
    @CrossOrigin
    @GetMapping("/getReservations/{eventLabel}")
    public String getReservationsByEventLabel(@PathVariable String eventLabel) {
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT DISTINCT ?reservation ?property ?value ?event ?res_date\n" +
                "WHERE {\n" +
                "  ?event a ns:Event.\n" +
                "  ?event ns:label \"" + eventLabel + "\".\n" +
                "  ?event ns:Event_has_reservation ?reservation.\n" +
                "  ?reservation ?property ?value.\n" +
                "  OPTIONAL { ?reservation ns:Res_Date ?res_date. }" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a set to hold unique JSON objects
        Set<JSONObject> jsonObjects = new HashSet<>();

        // Create a set to store unique reservation URIs
        Set<String> uniqueReservations = new HashSet<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            String eventURI = solution.get("event").toString();
            String reservationURI = solution.get("reservation").toString();

            // Check if the reservation URI is unique
            if (!uniqueReservations.contains(reservationURI)) {
                // Add the reservation URI to the set to mark it as seen
                uniqueReservations.add(reservationURI);

                // Create a new JSON object for each reservation
                JSONObject reservationObject = new JSONObject();
                reservationObject.put("Reservation", reservationURI);
                reservationObject.put("Event", eventURI);

                // Iterate over the reservation's properties and values
                String property = solution.get("property").toString();
                String value = solution.get("value").toString();

                // Extract property names and values
                String propertyName = property.substring(property.lastIndexOf("#") + 1);
                String propertyValue = value;

                // Remove "^^http://www.w3.org/2001/XMLSchema#dateTime" from Res_Date
                if (propertyName.equals("Res_Date")) {
                    propertyValue = propertyValue.split("\\^\\^")[0];
                }

                // Add property to the reservation object
                reservationObject.put(propertyName, propertyValue);

                // Check if Res_Date is present and add it
                if (solution.contains("res_date")) {
                    String resDate = solution.get("res_date").toString();
                    resDate = resDate.split("\\^\\^")[0];
                    reservationObject.put("Res_Date", resDate);
                }

                // Add the reservation object to the set
                jsonObjects.add(reservationObject);
            }
        }

        // Convert the set of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }
    @CrossOrigin
    @GetMapping("/getAllReservations")
    public String getAllReservations() {
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?reservation ?property ?value\n" +
                "WHERE {\n" +
                "  ?reservation a ns:Reservation.\n" +
                "  ?reservation ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a list to hold JSON objects
        List<JSONObject> jsonObjects = new ArrayList<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            // Create a JSON object for each event
            JSONObject reservtionObject = new JSONObject();

            String reservation = solution.get("reservation").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Extract just the date and time part without the extra information
            if (propertyValue.contains("^^http://www.w3.org/2001/XMLSchema#dateTime")) {
                propertyValue = propertyValue.split("\\^\\^")[0];
            }

            // Add property to the event object
            reservtionObject.put(propertyName, propertyValue);

            // Check if the event object already exists in the list
            boolean reservationExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(reservation)) {
                    jsonObject.getJSONObject(reservation).put(propertyName, propertyValue);
                    reservationExists = true;
                    break;
                }
            }

            if (!reservationExists) {
                // Create a new JSON object for the event and add it to the list
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(reservation, reservtionObject);
                jsonObjects.add(jsonObject);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }
    @GetMapping("/getEventsByCategory/{category}")
    public String getEventsByCategory(@PathVariable String category) {
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?event ?property ?value\n" +
                "WHERE {\n" +
                "  ?event a ns:Event.\n" +
                "  ?event ns:Category_event \"" + category + "\".\n" +  // Filter by category
                "  ?event ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a list to hold JSON objects
        List<JSONObject> jsonObjects = new ArrayList<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            // Create a JSON object for each event
            JSONObject eventObject = new JSONObject();

            String event = solution.get("event").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Extract just the date and time part without the extra information
            if (propertyValue.contains("^^http://www.w3.org/2001/XMLSchema#dateTime")) {
                propertyValue = propertyValue.split("\\^\\^")[0];
            }

            // Add property to the event object
            eventObject.put(propertyName, propertyValue);

            // Check if the event object already exists in the list
            boolean eventExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(event)) {
                    jsonObject.getJSONObject(event).put(propertyName, propertyValue);
                    eventExists = true;
                    break;
                }
            }

            if (!eventExists) {
                // Create a new JSON object for the event and add it to the list
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(event, eventObject);
                jsonObjects.add(jsonObject);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }

}























