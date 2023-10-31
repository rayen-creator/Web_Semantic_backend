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

@RequestMapping("/Announcement")
@CrossOrigin(origins = "http://localhost:4200")
public class AnnouncementController {


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
    @GetMapping("/getAnnouncementsByTag/{tag}")
    public String getAnnouncementsByTag(@PathVariable("tag") String tag) {
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?announcement ?property ?value\n" +
                "WHERE {\n" +
                "  ?announcement a ns:Announcement.\n" +
                "  ?announcement ns:Announcement_has_tags ns:" + tag + ".\n" + // Filter by tag
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
                String tagValue = propertyValue.substring(propertyValue.lastIndexOf("#") + 1);
                tagsArray.put(tagValue);
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


}
