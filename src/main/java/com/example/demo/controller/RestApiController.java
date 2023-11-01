package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.tools.JenaEngine;


@RestController
@RequestMapping("/controller")
@CrossOrigin(origins = "http://localhost:4200")
public class RestApiController {



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


    @GetMapping("/getAnnouncements")
    public String getAnnouncements() {

        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?announcement ?property ?value\n" +
                "WHERE {\n" +
                "  ?announcement a ns:Announcement.\n" +
                "  ?announcement ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a list to hold JSON objects
        List<JSONObject> jsonObjects = new ArrayList<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            String announc = solution.get("announcement").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Create a JSON object for each announcement
            JSONObject announcObject = jsonObjects.stream()
                    .filter(obj -> obj.has(announc))
                    .findFirst()
                    .orElseGet(() -> {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(announc, new JSONObject());
                        jsonObjects.add(jsonObject);
                        return jsonObject;
                    });

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Handle tags separately
            if (propertyName.equals("Announcement_has_tags")) {
                JSONArray tagsArray = announcObject.getJSONObject(announc).optJSONArray("Announcement_has_tags");
                if (tagsArray == null) {
                    tagsArray = new JSONArray();
                }
                String tag = propertyValue.substring(propertyValue.lastIndexOf("#") + 1);
                tagsArray.put(tag);
                announcObject.getJSONObject(announc).put("Announcement_has_tags", tagsArray);
            } else {
                // Add property to the announcement object
                announcObject.getJSONObject(announc).put(propertyName, propertyValue);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }

    @GetMapping("/getAnnouncementById/{announcementId}")
    public String getAnnouncementById(@PathVariable("announcementId") String announcementId) {
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?property ?value\n" +
                "WHERE {\n" +
                "  ns:" + announcementId + " ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a JSON object to hold the announcement properties
        JSONObject announcementObject = new JSONObject();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Add property to the announcement object
            announcementObject.put(propertyName, propertyValue);
        }

        return announcementObject.toString();
    }





}
