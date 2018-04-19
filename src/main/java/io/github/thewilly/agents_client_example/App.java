package io.github.thewilly.agents_client_example;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.github.thewilly.agents_client_example.types.Agent;

/**
 * Hello world!
 *
 */
public class App {

    // This is the entry point for all external connections from clients.
    // The /agents_service points to the corresponding service to use.
    // All services are reachable as /<service-name>_service
    // Then we can access the service controllers as
    // /<service-name>_service/<controller-name>[/args | ?params]
    static String API_GATEWAY = "http://asw-i3a-zuul-eu-west-1.guill.io/agents_service";

    public static void main(String[] args)
	    throws UnirestException, JsonParseException, JsonMappingException, IOException {

	// Example 1
	// Getting a collection of mapped elements from a micro-service.

	List<Agent> agents = gettingACollectionOfElements();
	for (Agent a : agents) {
	    System.err.println(">>>>>>>>>>>>>> " + a.toString());
	}

	// Example 1.1
	// Getting a collection of mapped elements from a micro-service.

	Agent[] collectionOfAgents = gettingACollectionOfElementsWithRestTemplate();
	for (Agent a : collectionOfAgents) {
	    System.err.println(">>>>>>>>>>>>>> " + a.toString());
	}

	// Example 2
	// Getting one single element as a mapped object from the micro-service

	Agent agent = gettingOneSingleObject(agents.get(0).getDb_id());
	System.err.println(">>>>>>>>>>>>>> " + agent);

	// Example 3
	// Getting a single element as a JSONObject. Much MUCH faster and flexible than
	// working with mapped objects.

	JSONObject node = gettingOneSingleObjectAsAJSONObject(agents.get(0).getDb_id());
	System.err.println(">>>>>>>>>>>>>> " + node.get("db_id") + " " + node.get("name") + " " + node.get("location")
		+ " " + node.get("email") + " " + node.get("id") + " " + node.get("kind") + " " + node.get("kindCode"));

	// Example 4
	// Getting a single element as a Map, same as JSON approach.

	Map<String, Object> loginCredentials = new HashMap<String, Object>();
	loginCredentials.put("login", "45170000A");
	loginCredentials.put("password", "4[[j[frVCUMJ>hU");
	loginCredentials.put("kind", 1);
	someRequestThatNeedsAPayload(loginCredentials);

    }

    /**
     * EXAMPLE 1
     */
    public static List<Agent> gettingACollectionOfElements()
	    throws UnirestException, JsonParseException, JsonMappingException, IOException {

	// We use Unirest as a library to connect with the micro-service.

	HttpResponse<JsonNode> response = Unirest.post(API_GATEWAY + "/agents").asJson();
	ObjectMapper mapper = new ObjectMapper();

	List<Agent> items = mapper.readValue(response.getBody().toString(),
		mapper.getTypeFactory().constructParametricType(List.class, Agent.class));

	return items;
    }

    /**
     * EXAMPLE 1.1
     */
    public static Agent[] gettingACollectionOfElementsWithRestTemplate()
	    throws UnirestException, JsonParseException, JsonMappingException, IOException {

	// We use rest template as a source to connect with the micro-service.

	Agent[] items = new RestTemplate().getForObject(API_GATEWAY + "/agents", Agent[].class);

	return items;
    }

    /**
     * EXAMPLE 2
     */
    public static void someRequestThatNeedsAPayload(Map<String, Object> payload) throws UnirestException {
	HttpResponse<JsonNode> response = Unirest.post(API_GATEWAY + "/auth").header("Content-Type", "application/json")
		.body(new JSONObject(payload)).asJson();

	if (response.getStatus() == HttpStatus.SC_OK) {
	    System.err.println(">>>>>>>>>>>>>> " + "CONGRATULATIONS LOGIN CORRECT");
	} else {
	    System.err.println(">>>>>>>>>>>>>> " + "SORRY LOGIN FAILED");
	}
    }

    /**
     * EXAMPLE 3
     */
    public static JSONObject gettingOneSingleObjectAsAJSONObject(String id) throws UnirestException {
	return Unirest.post(API_GATEWAY + "/agents/" + id).asJson().getBody().getObject();
    }

    /**
     * EXAMPLE 4
     */
    public static Agent gettingOneSingleObject(String id)
	    throws UnirestException, JsonParseException, JsonMappingException, IOException {
	HttpResponse<JsonNode> response = Unirest.post(API_GATEWAY + "/agents/" + id).asJson();
	ObjectMapper mapper = new ObjectMapper();

	return mapper.readValue(response.getBody().toString(), Agent.class);
    }
}
