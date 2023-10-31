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
    @GetMapping("/getblogfilter")
    public String getBlogsfilter() {

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
                "  ?blog ns:Blog_post_has_author ns:Sarra_Tounsi.\n" + // Add this line to filter by author
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


    @GetMapping("/getcomments")
    public String getComments() {

//        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
//                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
//                "\n" +
//                "SELECT ?user\n" +
//                "WHERE {\n" +
//                "?user rdf:type ns:Admin .\n" +
//                "}";
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?comment ?property ?value\n" +
                "WHERE {\n" +
                "  ?comment a ns:Blog_Comment.\n" +
                "  ?comment ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a list to hold JSON objects
        List<JSONObject> jsonObjects = new ArrayList<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            // Create a JSON object for each comment
            JSONObject commentObject = new JSONObject();

            String comment = solution.get("comment").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Add property to the comment object
            commentObject.put(propertyName, propertyValue);

            // Check if the comment object already exists in the list
            boolean commentExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(comment)) {
                    jsonObject.getJSONObject(comment).put(propertyName, propertyValue);
                    commentExists = true;
                    break;
                }
            }

            if (!commentExists) {
                // Create a new JSON object for the blog and add it to the list
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(comment, commentObject);
                jsonObjects.add(jsonObject);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }

    @GetMapping("/getcommentspypost/{postUri}")
    public String getCommentsnypost(@PathVariable("postUri") String postUri) {
        // Define the URI of the post you want to filter comments by
       // String postUri = "SpecifyThePostURIHere"; // Replace with the actual post URI

        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?comment ?property ?value\n" +
                "WHERE {\n" +
                "  ?comment a ns:Blog_Comment.\n" +
                "  ?comment ns:comment_belongs_to_post ns:" + postUri + ".\n" + // Filter by post
                "  ?comment ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a list to hold JSON objects
        List<JSONObject> jsonObjects = new ArrayList<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            // Create a JSON object for each comment
            JSONObject commentObject = new JSONObject();

            String comment = solution.get("comment").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Add property to the comment object
            commentObject.put(propertyName, propertyValue);

            // Check if the comment object already exists in the list
            boolean commentExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(comment)) {
                    jsonObject.getJSONObject(comment).put(propertyName, propertyValue);
                    commentExists = true;
                    break;
                }
            }

            if (!commentExists) {
                // Create a new JSON object for the blog and add it to the list
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(comment, commentObject);
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
