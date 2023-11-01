package com.example.demo.controller;

import com.example.demo.tools.JenaEngine;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/blog")
@CrossOrigin(origins = "http://localhost:4200")
public class BlogController {

    @CrossOrigin
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
    @CrossOrigin
    @GetMapping("/getBlogsbycat/{Category}")
    public String getBlogsbycat(@PathVariable("Category") String Category) {


        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?blog ?property ?value\n" +
                "WHERE {\n" +
                "  ?blog a ns:Blog.\n" +
                "  ?blog ns:Category \"" + Category + "\".\n" +  // Use double quotes for the Category
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
    @CrossOrigin
    @GetMapping("/getBlogsbytitle/{title}")
    public String getBlogsbytitle(@PathVariable("title") String title) {


        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?blog ?property ?value\n" +
                "WHERE {\n" +
                "  ?blog a ns:Blog.\n" +
                "  ?blog ns:Title \"" + title + "\".\n" +  // Use double quotes for the Category
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

    @CrossOrigin
    @GetMapping("/getblogbyauthor/{authorUri}")
    public String getblogbyauthor(@PathVariable("authorUri") String authorUri) {


        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?blog ?property ?value\n" +
                "WHERE {\n" +
                "  ?blog a ns:Blog.\n" +
                "  ?blog ns:Blog_post_has_author ns:" + authorUri + ".\n" + // Add this line to filter by author
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


    @CrossOrigin
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

    @CrossOrigin
    @GetMapping("/getcommentspypost/{postUri}")
    public String getCommentsnypost(@PathVariable("postUri") String postUri) {

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

}
