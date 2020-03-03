package nl.tudelft.oopp.demo.communication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ServerCommunication {

    private static HttpClient client = HttpClient.newHttpClient();
    private static String host = "http://localhost:8080";

    /**
     * Retrieves a quote from the server.
     * @return the body of a get request to the server.
     */
    public static String getQuote() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/quote")).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return response.body();
    }

    /**
     * Sends the server a simple GET request.
     * @return the body of a get request of the server.
     */
    public static String ping() {
        // Send GET request to host at path /ping
        HttpResponse<String> response = HttpRequestHandler.get(host,"ping");
        return response.body();
    }

    /** Sends the server a simple POST request.
     * @param input the input parameter to be relayed back.
     * @return "Your input: " plus the body of the POST request.
     */
    public static String relay(String input) {

        // Put parameters into Map.
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("input",input);
        // Send POST request to host at path /relay
        HttpResponse<String> response = HttpRequestHandler.post(host, "relay", paramMap);
        return response.body();
    }
}
