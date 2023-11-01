package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.util.*;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.tools.JenaEngine;


@RestController
@RequestMapping("/controller")
@CrossOrigin(origins = "http://localhost:4200")
public class RestApiController {

    @GetMapping("/getblogs")
    public String getBlogs() {

//        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
//                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
//                "\n" +
//                "SELECT ?user\n" +
//                "WHERE {\n" +
//                "?user rdf:type ns:Admin .\n" +
//                "}";
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?blog ?property ?value\n" +
                "WHERE {\n" +
                "  ?blog a ns:Blog.\n" +
                "  ?blog ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a list to hold JSON objects
        List<JSONObject> jsonObjects = new ArrayList<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            // Create a JSON object for each blog
            JSONObject blogObject = new JSONObject();

            String blog = solution.get("blog").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Add property to the blog object
            blogObject.put(propertyName, propertyValue);

            // Check if the blog object already exists in the list
            boolean blogExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(blog)) {
                    jsonObject.getJSONObject(blog).put(propertyName, propertyValue);
                    blogExists = true;
                    break;
                }
            }

            if (!blogExists) {
                // Create a new JSON object for the blog and add it to the list
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(blog, blogObject);
                jsonObjects.add(jsonObject);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }

    @GetMapping("/getcompany")
    public String getcompany() {

//        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
//                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
//                "\n" +
//                "SELECT ?user\n" +
//                "WHERE {\n" +
//                "?user rdf:type ns:Admin .\n" +
//                "}";
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?company ?property ?value\n" +
                "WHERE {\n" +
                "  ?company a ns:Company.\n" +
                "  ?company ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a list to hold JSON objects
        List<JSONObject> jsonObjects = new ArrayList<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            // Create a JSON object for each blog
            JSONObject blogObject = new JSONObject();

            String blog = solution.get("company").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Add property to the blog object
            blogObject.put(propertyName, propertyValue);

            // Check if the blog object already exists in the list
            boolean blogExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(blog)) {
                    jsonObject.getJSONObject(blog).put(propertyName, propertyValue);
                    blogExists = true;
                    break;
                }
            }

            if (!blogExists) {
                // Create a new JSON object for the blog and add it to the list
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(blog, blogObject);
                jsonObjects.add(jsonObject);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }

    @GetMapping("/getJob")
    public String getJob() {

//        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
//                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
//                "\n" +
//                "SELECT ?user\n" +
//                "WHERE {\n" +
//                "?user rdf:type ns:Admin .\n" +
//                "}";
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?job ?property ?value\n" +
                "WHERE {\n" +
                "  ?job a ns:Job.\n" +
                "  ?job ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a list to hold JSON objects
        List<JSONObject> jsonObjects = new ArrayList<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            // Create a JSON object for each blog
            JSONObject blogObject = new JSONObject();

            String blog = solution.get("job").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Add property to the blog object
            blogObject.put(propertyName, propertyValue);

            // Check if the blog object already exists in the list
            boolean blogExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(blog)) {
                    jsonObject.getJSONObject(blog).put(propertyName, propertyValue);
                    blogExists = true;
                    break;
                }
            }

            if (!blogExists) {
                // Create a new JSON object for the blog and add it to the list
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(blog, blogObject);
                jsonObjects.add(jsonObject);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
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


    @GetMapping("/getAttendees/{eventLabel}")
    public String getAttendeesByEventLabel(@PathVariable String eventLabel) {
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?event ?attendee\n" +
                "WHERE {\n" +
                "  ?event a ns:Event.\n" +
                "  ?event ns:label \"" + eventLabel + "\".\n" +  // Use double quotes for the label
                "  ?event ns:Event_has__attendees ?attendee.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a list to hold JSON objects
        List<JSONObject> jsonObjects = new ArrayList<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            String attendeeURI = solution.get("attendee").toString();

            // Query for the attendee's complete information
            String attendeeQuery = "SELECT ?property ?value WHERE { <" + attendeeURI + "> ?property ?value }";
            QueryExecution attendeeQe = QueryExecutionFactory.create(attendeeQuery, model);
            ResultSet attendeeResults = attendeeQe.execSelect();

            // Create a new JSON object for each attendee
            JSONObject attendeeObject = new JSONObject();

            // Add the attendee's URI to the object
            attendeeObject.put("Attendee", attendeeURI);

            // Iterate over the attendee's properties and values
            while (attendeeResults.hasNext()) {
                QuerySolution attendeeSolution = attendeeResults.nextSolution();
                String property = attendeeSolution.get("property").toString();
                String value = attendeeSolution.get("value").toString();

                // Extract property names and values
                String propertyName = property.substring(property.lastIndexOf("#") + 1);
                String propertyValue = value;

                // Add property to the attendee object
                attendeeObject.put(propertyName, propertyValue);
            }

            // Add the attendee object to the list
            jsonObjects.add(attendeeObject);
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }

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

    @GetMapping("/getAllReservations")
    public String getAllReservations() {
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT DISTINCT ?reservation ?property ?value ?event ?resDate\n" +
                "WHERE {\n" +
                "  ?event a ns:Event.\n" +
                "  ?event ns:Event_has_reservation ?reservation.\n" +
                "  ?reservation ?property ?value.\n" +
                "  ?reservation ns:Res_Date ?resDate.\n" + // Add this line to retrieve Res_Date
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a list to hold JSON objects
        List<JSONObject> jsonObjects = new ArrayList<>();

        // Create a set to store unique reservation URIs
        Set<String> uniqueReservations = new HashSet<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            String eventURI = solution.get("event").toString();
            String reservationURI = solution.get("reservation").toString();
            String resDateWithDataType = solution.get("resDate").toString();

            // Remove the datatype information from Res_Date
            String[] parts = resDateWithDataType.split("\\^\\^");
            String resDate = parts[0].trim();

            // Check if the reservation URI is unique
            if (!uniqueReservations.contains(reservationURI)) {
                // Add the reservation URI to the set to mark it as seen
                uniqueReservations.add(reservationURI);

                // Create a new JSON object for each reservation
                JSONObject reservationObject = new JSONObject();
                reservationObject.put("Reservation", reservationURI);
                reservationObject.put("Event", eventURI);
                reservationObject.put("Res_Date", resDate); // Use the cleaned Res_Date

                // Iterate over the reservation's properties and values
                String property = solution.get("property").toString();
                String value = solution.get("value").toString();

                // Extract property names and values
                String propertyName = property.substring(property.lastIndexOf("#") + 1);
                String propertyValue = value;

                // Add property to the reservation object
                reservationObject.put(propertyName, propertyValue);

                // Add the reservation object to the list
                jsonObjects.add(reservationObject);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }

























}