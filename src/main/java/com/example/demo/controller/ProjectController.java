package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import com.example.demo.tools.JenaEngine;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

	@GetMapping("/getprojectss")
    public String getProjects() {

//        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
//                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
//                "\n" +
//                "SELECT ?user\n" +
//                "WHERE {\n" +
//                "?user rdf:type ns:Admin .\n" +
//                "}";
        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
                "SELECT ?project ?property ?value\n" +
                "WHERE {\n" +
                "  ?project a ns:Project.\n" +
                "  ?project ?property ?value.\n" +
                "}";

        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a list to hold JSON objects
        List<JSONObject> jsonObjects = new ArrayList<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            // Create a JSON object for each project
            JSONObject projectObject = new JSONObject();

            String project = solution.get("project").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Add property to the project object
            projectObject.put(propertyName, propertyValue);

            // Check if the project object already exists in the list
            boolean projectExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(project)) {
                    jsonObject.getJSONObject(project).put(propertyName, propertyValue);
                    projectExists = true;
                    break;
                }
            }

            if (!projectExists) {
                // Create a new JSON object for the project and add it to the list
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(project, projectObject);
                jsonObjects.add(jsonObject);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }
    
    // tasks
	
	@GetMapping("/gettasks")
    public String getTasks() {

//        String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
//                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
//                "\n" +
//                "SELECT ?user\n" +
//                "WHERE {\n" +
//                "?user rdf:type ns:Admin .\n" +
//                "}";
		String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
	               "SELECT ?task ?property ?value\n" +
	               "WHERE {\n" +
	               "  ?task a ns:Task.\n" + // Adjust the class here to Task or the relevant class in your ontology
	               "  ?task ?property ?value.\n" +
	               "}";


        Model model = JenaEngine.readModel("data/freelance.owl");

        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
        ResultSet results = qe.execSelect();

        // Create a list to hold JSON objects
        List<JSONObject> jsonObjects = new ArrayList<>();

        // Iterate over the query results
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();

            // Create a JSON object for each task
            JSONObject taskObject = new JSONObject();

            String task = solution.get("task").toString();
            String property = solution.get("property").toString();
            String value = solution.get("value").toString();

            // Extract property names and values
            String propertyName = property.substring(property.lastIndexOf("#") + 1);
            String propertyValue = value;

            // Add property to the task object
            taskObject.put(propertyName, propertyValue);

            // Check if the task object already exists in the list
            boolean taskExists = false;
            for (JSONObject jsonObject : jsonObjects) {
                if (jsonObject.has(task)) {
                    jsonObject.getJSONObject(task).put(propertyName, propertyValue);
                    taskExists = true;
                    break;
                }
            }

            if (!taskExists) {
                // Create a new JSON object for the Project and add it to the list
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(task, taskObject);
                jsonObjects.add(jsonObject);
            }
        }

        // Convert the list of JSON objects to a JSON array
        JSONArray jsonArray = new JSONArray(jsonObjects);

        return jsonArray.toString();
    }
	
	@GetMapping("/gettaskspyproject/{Pro_Name:.+}")
	public String getTasksnyproject(@PathVariable("Pro_Name") String Pro_Name) {

		String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
	               "SELECT ?task ?property ?value\n" +
	               "WHERE {\n" +
	               "  ?project a ns:Project.\n" + // This line selects projects
	               "  ?project ns:Pro_Name ?projectName.\n" + // Extract the project name
	               "  FILTER(?projectName = '" + Pro_Name + "').\n" + // Filter by project name
	               "  ?project ns:projet_has_task ?task.\n" + // Select tasks related to the project
	               "  ?task ?property ?value.\n" +
	               "}";


	        Model model = JenaEngine.readModel("data/freelance.owl");

	        QueryExecution qe = QueryExecutionFactory.create(qexec, model);
	        ResultSet results = qe.execSelect();

	        // Create a list to hold JSON objects
	        List<JSONObject> jsonObjects = new ArrayList<>();

	        // Iterate over the query results
	        while (results.hasNext()) {
	            QuerySolution solution = results.nextSolution();

	            // Create a JSON object for each task
	            JSONObject taskObject = new JSONObject();

	            String task = solution.get("task").toString();
	            String property = solution.get("property").toString();
	            String value = solution.get("value").toString();

	            // Extract property names and values
	            String propertyName = property.substring(property.lastIndexOf("#") + 1);
	            String propertyValue = value;

	            // Add property to the task object
	            taskObject.put(propertyName, propertyValue);

	            // Check if the task object already exists in the list
	            boolean taskExists = false;
	            for (JSONObject jsonObject : jsonObjects) {
	                if (jsonObject.has(task)) {
	                    jsonObject.getJSONObject(task).put(propertyName, propertyValue);
	                    taskExists = true;
	                    break;
	                }
	            }

	            if (!taskExists) {
	                // Create a new JSON object for the Project and add it to the list
	                JSONObject jsonObject = new JSONObject();
	                jsonObject.put(task, taskObject);
	                jsonObjects.add(jsonObject);
	            }
	        }

	        // Convert the list of JSON objects to a JSON array
	        JSONArray jsonArray = new JSONArray(jsonObjects);

	        return jsonArray.toString();
	    }
	 
	@GetMapping("/getprojectbyProName/{Pro_Name}")
	public String getProjectByProName(@PathVariable("Pro_Name") String projectName) {

	    String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
	            "SELECT ?project ?property ?value\n" +
	            "WHERE {\n" +
	            "  ?project a ns:Project.\n" +
	            "  ?project ns:Pro_Name ?projectName.\n" + // Filter by project name
	            "  FILTER(?projectName = '" + projectName + "').\n" +
	            "  ?project ?property ?value.\n" +
	            "}";

	    Model model = JenaEngine.readModel("data/freelance.owl");

	    QueryExecution qe = QueryExecutionFactory.create(qexec, model);
	    ResultSet results = qe.execSelect();

	    // Create a list to hold JSON objects
	    List<JSONObject> jsonObjects = new ArrayList<>();

	    // Iterate over the query results
	    while (results.hasNext()) {
	        QuerySolution solution = results.nextSolution();

	        // Create a JSON object for each project
	        JSONObject projectObject = new JSONObject();

	        String project = solution.get("project").toString();
	        String property = solution.get("property").toString();
	        String value = solution.get("value").toString();

	        // Extract property names and values
	        String propertyName = property.substring(property.lastIndexOf("#") + 1);
	        String propertyValue = value;

	        // Add property to the project object
	        projectObject.put(propertyName, propertyValue);

	        // Check if the project object already exists in the list
	        boolean projectExists = false;
	        for (JSONObject jsonObject : jsonObjects) {
	            if (jsonObject.has(project)) {
	                jsonObject.getJSONObject(project).put(propertyName, propertyValue);
	                projectExists = true;
	                break;
	            }
	        }

	        if (!projectExists) {
	            // Create a new JSON object for the project and add it to the list
	            JSONObject jsonObject = new JSONObject();
	            jsonObject.put(project, projectObject);
	            jsonObjects.add(jsonObject);
	        }
	    }

	    // Convert the list of JSON objects to a JSON array
	    JSONArray jsonArray = new JSONArray(jsonObjects);

	    return jsonArray.toString();
	}

	@GetMapping("/getprojectsbystatus/{status}")
	public String getProjectsByStatus(@PathVariable("status") String status) {
	    String qexec = "PREFIX ns: <http://www.semanticweb.org/houssem/ontologies/2023/9/untitled-ontology-3#>\n" +
	            "SELECT ?project ?property ?value\n" +
	            "WHERE {\n" +
	            "  ?project a ns:Project.\n" +
	            "  ?project ns:status \"" + status + "\".\n" + // Use double quotes for the status
	            "  ?project ?property ?value.\n" +
	            "}";

	    Model model = JenaEngine.readModel("data/freelance.owl");

	    QueryExecution qe = QueryExecutionFactory.create(qexec, model);
	    ResultSet results = qe.execSelect();

	    // Create a list to hold JSON objects
	    List<JSONObject> jsonObjects = new ArrayList<>();

	    // Iterate over the query results
	    while (results.hasNext()) {
	        QuerySolution solution = results.nextSolution();

	        // Create a JSON object for each project
	        JSONObject projectObject = new JSONObject();

	        String project = solution.get("project").toString();
	        String property = solution.get("property").toString();
	        String value = solution.get("value").toString();

	        // Extract property names and values
	        String propertyName = property.substring(property.lastIndexOf("#") + 1);
	        String propertyValue = value;

	        // Add property to the project object
	        projectObject.put(propertyName, propertyValue);

	        // Check if the project object already exists in the list
	        boolean projectExists = false;
	        for (JSONObject jsonObject : jsonObjects) {
	            if (jsonObject.has(project)) {
	                jsonObject.getJSONObject(project).put(propertyName, propertyValue);
	                projectExists = true;
	                break;
	            }
	        }

	        if (!projectExists) {
	            // Create a new JSON object for the project and add it to the list
	            JSONObject jsonObject = new JSONObject();
	            jsonObject.put(project, projectObject);
	            jsonObjects.add(jsonObject);
	        }
	    }

	    // Convert the list of JSON objects to a JSON array
	    JSONArray jsonArray = new JSONArray(jsonObjects);

	    return jsonArray.toString();
	}

}
