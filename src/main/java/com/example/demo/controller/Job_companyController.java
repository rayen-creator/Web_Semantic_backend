package com.example.demo.controller;

import com.example.demo.tools.JenaEngine;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/controller")
@CrossOrigin(origins = "http://localhost:4200")
public class Job_companyController {

    @CrossOrigin
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
            JSONObject companyObject = new JSONObject();

            String company = solution.get("company").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Add property to the blog object
            companyObject.put(propertyName, propertyValue);

            // Check if the blog object already exists in the list
            boolean blogExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(company)) {
                    jsonObject.getJSONObject(company).put(propertyName, propertyValue);
                    blogExists = true;
                    break;
                }
            }

            if (!blogExists) {
                // Create a new JSON object for the blog and add it to the list
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(company, companyObject);
                jsonObjects.add(jsonObject);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }
    @CrossOrigin
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
            JSONObject jobObject = new JSONObject();

            String job = solution.get("job").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Add property to the blog object
            jobObject.put(propertyName, propertyValue);

            // Check if the blog object already exists in the list
            boolean blogExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(job)) {
                    jsonObject.getJSONObject(job).put(propertyName, propertyValue);
                    blogExists = true;
                    break;
                }
            }

            if (!blogExists) {
                // Create a new JSON object for the blog and add it to the list
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(job, jobObject);
                jsonObjects.add(jsonObject);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }

    @GetMapping("/getJobBycompany/{Name_Company}")
    public String getJobBycompany (@PathVariable("Name_Company") String Name_Company) {

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
                "?job a ns:Job.\n" +
                "?job ns:job_belongs_to_company ns:" + Name_Company + ".\n" +
                "?job ?property ?value.\n" +
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
            JSONObject jobObject = new JSONObject();

            String job = solution.get("job").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Add property to the blog object
            jobObject.put(propertyName, propertyValue);

            // Check if the blog object already exists in the list
            boolean blogExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(job)) {
                    jsonObject.getJSONObject(job).put(propertyName, propertyValue);
                    blogExists = true;
                    break;
                }
            }

            if (!blogExists) {
                // Create a new JSON object for the blog and add it to the list
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(job, jobObject);
                jsonObjects.add(jsonObject);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }


}
