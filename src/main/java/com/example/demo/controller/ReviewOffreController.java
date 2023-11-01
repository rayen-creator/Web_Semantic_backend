package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import com.example.demo.tools.JenaEngine;


@RestController

@RequestMapping("/ReviewContrat")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewOffreController {

    @GetMapping("/getcontrats")
    public String getcontrats() {

        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?contrat ?property ?value\n" +
                "WHERE {\n" +
                "  ?contrat a ns:Contrat.\n" +
                "  ?contrat ?property ?value.\n" +
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
            JSONObject eventObject = new JSONObject();

            String event = solution.get("contrat").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            eventObject.put(propertyName, propertyValue);

            boolean eventExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(event)) {
                    jsonObject.getJSONObject(event).put(propertyName, propertyValue);
                    eventExists = true;
                    break;
                }
            }

            if (!eventExists) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(event, eventObject);
                jsonObjects.add(jsonObject);
            }
        }

        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }
    @GetMapping("/getContratByType/{type_contrat}")
    public ResponseEntity<Object> getContratByType(@PathVariable String type_contrat) {
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?contrat ?property ?value\n" +
                "WHERE {\n" +
                "  ?contrat a ns:Contrat.\n" +
                "  ?contrat ns:type_contrat \"" + type_contrat + "\".\n" +  // Use double quotes for the label
                "  ?contrat ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        JSONObject eventObject = new JSONObject();

        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            String event = solution.get("contrat").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            eventObject.put(propertyName, propertyValue);
        }

        if (eventObject.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(eventObject.toString());
        }
    }

    @GetMapping("/getReviewByType/{type_contrat}")
    public ResponseEntity<Object> getReviewByType(@PathVariable String type_contrat) {
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?contrat ?property ?value\n" +
                "WHERE {\n" +
                "  ?contrat a ns:Contrat.\n" +
                "  ?contrat ns:type_contrat \"" + type_contrat + "\".\n" +  // Use double quotes for the label
                "  ?contrat ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        JSONObject eventObject = new JSONObject();

        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            String event = solution.get("contrat").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            eventObject.put(propertyName, propertyValue);
        }

        if (eventObject.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(eventObject.toString());
        }
    }

    @GetMapping("/getreviews")
    public String getReviews() {

        String userURI = "<http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#Sarra_Tounsi>";
        String userQuery = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?review\n" +
                "WHERE {\n" +
                userURI + " ns:User_Add_Review ?review.\n" +
                "}";


        Model model = JenaEngine.readModel("data/freelance.owl");

        Query query = QueryFactory.create(userQuery);
        QueryExecution userQueryExecution = QueryExecutionFactory.create(query, model);
        ResultSet userResults = userQueryExecution.execSelect();

        List<JSONObject> jsonObjects = new ArrayList<>();

        while (userResults.hasNext()) {
            QuerySolution userSolution = userResults.nextSolution();

            Resource reviewResource = userSolution.getResource("review");

            String reviewQuery = "SELECT ?property ?value\n" +
                    "WHERE {\n" +
                    "<" + reviewResource.getURI() + "> ?property ?value.\n" +
                    "}";

            QueryExecution reviewQueryExecution = QueryExecutionFactory.create(reviewQuery, model);
            ResultSet reviewResults = reviewQueryExecution.execSelect();

            JSONObject reviewObject = new JSONObject();

            while (reviewResults.hasNext()) {
                QuerySolution reviewSolution = reviewResults.nextSolution();

                RDFNode propertyNode = reviewSolution.get("property");
                RDFNode valueNode = reviewSolution.get("value");

                String property = propertyNode.toString();
                String value = valueNode.toString();

                String propertyName = property.substring(property.lastIndexOf("#") + 1);
                String propertyValue = value;

                reviewObject.put(propertyName, propertyValue);
            }

            jsonObjects.add(reviewObject);
        }

        // Convertissez la liste d'objets JSON en un tableau JSON
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }

    @GetMapping("/getReviewsByCompany/{companyName}")
    public String getReviewsByCompany(@PathVariable String companyName) {
        // Create a query to retrieve the company URI based on the company name
        String companyQuery = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?company\n" +
                "WHERE {\n" +
                "?company a ns:Company.\n" +
                "?company ns:Name_Company \"" + companyName + "\".\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution companyQueryExecution = QueryExecutionFactory.create(companyQuery, model);
        ResultSet companyResults = companyQueryExecution.execSelect();

        // Create a list to hold JSON objects for reviews
        List<JSONObject> jsonObjects = new ArrayList<>();

        while (companyResults.hasNext()) {
            QuerySolution companySolution = companyResults.nextSolution();
            Resource companyResource = companySolution.getResource("company");

            // Query for reviews associated with the company URI
            String reviewQuery = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                    "SELECT ?review\n" +
                    "WHERE {\n" +
                    "<" + companyResource.getURI() + "> ns:Class_has_review ?review.\n" +
                    "}";

            QueryExecution reviewQueryExecution = QueryExecutionFactory.create(reviewQuery, model);
            ResultSet reviewResults = reviewQueryExecution.execSelect();

            while (reviewResults.hasNext()) {
                QuerySolution reviewSolution = reviewResults.nextSolution();
                Resource reviewResource = reviewSolution.getResource("review");

                // Query for review properties and values
                String propertyValueQuery = "SELECT ?property ?value\n" +
                        "WHERE {\n" +
                        "<" + reviewResource.getURI() + "> ?property ?value.\n" +
                        "}";

                QueryExecution propertyValueQueryExecution = QueryExecutionFactory.create(propertyValueQuery, model);
                ResultSet propertyValueResults = propertyValueQueryExecution.execSelect();

                JSONObject reviewObject = new JSONObject();

                while (propertyValueResults.hasNext()) {
                    QuerySolution propertyValueSolution = propertyValueResults.nextSolution();
                    RDFNode propertyNode = propertyValueSolution.get("property");
                    RDFNode valueNode = propertyValueSolution.get("value");

                    String property = propertyNode.toString();
                    String value = valueNode.toString();

                    String propertyName = property.substring(property.lastIndexOf("#") + 1);
                    String propertyValue = value;

                    reviewObject.put(propertyName, propertyValue);
                }

                jsonObjects.add(reviewObject);
            }
        }

        // Convert the list of JSON objects into a JSON array
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



}
