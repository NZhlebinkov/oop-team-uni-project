package nl.tudelft.oopp.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jdi.request.InvalidRequestStateException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import nl.tudelft.oopp.api.models.User;

public class HttpRequestHandler {
    private String host;
    private HttpClient client;
    private ObjectMapper objectMapper;
    public static User user;

    public static void saveUser(User input) {
        user = input;
    }

    /**
     * For testing purposes, this class isn't static, but instead has this.
     */
    public HttpRequestHandler() {
        this.client = HttpClient.newHttpClient();
        this.host = "http://localhost:8080";
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Creates a new HttpRequestHandler with an HttpClient. Used for unit testing.
     * @param client the (mock) HttpClient.
     */
    public HttpRequestHandler(HttpClient client) {
        this.host = "http://localhost:8080";
        this.objectMapper = new ObjectMapper();
        this.client = client;
    }



    /**
     * Sends a POST request with some given parameters.
     *
     * @param path       the path on the server where the request should be sent.
     * @param parameters a map containing all parameters in the request, mapped as 'name,value'.
     * @return An HttpResponse object.
     */
    public <T, E> E post(String path, T parameters, Class<E> responseType) {
        // Build HTTP request
        HttpRequest request = null;

        try {
            String s = objectMapper.writeValueAsString(parameters);
            request = HttpRequest.newBuilder().uri(URI.create(host + "/" + path))
                    .setHeader("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(s)).build();
            String r = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return objectMapper.readValue(r, responseType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Sends a POST request with some given parameters that gets a list back.
     *
     * @param path       the path on the server where the request should be sent.
     * @param parameters a map containing all parameters in the request, mapped as 'name,value'.
     * @return An HttpResponse object.
     */
    public <T, E> List<E> postList(String path, T parameters, Class<E> responseType) {
        // Build HTTP request
        HttpRequest request = null;

        try {
            String s = objectMapper.writeValueAsString(parameters);
            request = HttpRequest.newBuilder().uri(URI.create(host + "/" + path))
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(s)).build();
            String r = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            TypeFactory factory = objectMapper.getTypeFactory();
            CollectionType listType = factory.constructCollectionType(List.class, responseType);
            return objectMapper.readValue(r, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Sends a PUT request with some given parameters.
     *
     * @param path       the path on the server where the request should be sent.
     * @param parameters a map containing all parameters in the request, mapped as 'name,value'.
     * @return An HttpResponse object.
     */
    public <T, E> E put(String path, T parameters, Class<E> responseType) {
        // Build HTTP request
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder().uri(URI.create(host + "/" + path))
                    .setHeader("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(parameters))).build();
            String r = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return objectMapper.readValue(r, responseType);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sends a GET request.
     *
     * @param path the path on the server where the request should be sent.
     * @return An HttpResponse object.
     */
    public <E> E get(String path, Class<E> responseType) {
        // Build HTTP request
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(host + "/" + path))
                .setHeader("Content-Type", "application/json").GET().build();
        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(httpResponse.body(), responseType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Sends a GET request that returns a list with responseType.
     *
     * @param path the path on the server where the request should be sent.
     * @return An HttpResponse object.
     */
    public <E> List<E> getList(String path, Class<E> responseType) {
        // Build HTTP request
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(host + "/" + path))
            .setHeader("Content-Type", "application/json").GET().build();
        try {
            TypeFactory factory = objectMapper.getTypeFactory();
            CollectionType listType = factory.constructCollectionType(List.class, responseType);
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(httpResponse.body(), listType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**     Sends a POST request to the server and gets the response. This method is used only
     *      for deleting items in the database. It is a POST, not DELETE request to allow a body
     *      to be sent with the user information to be validated and authorized.
     *
     * @param path              The request path.
     * @param parameters        The request body.
     * @param responseType      The expected {@link Class} of the response body.
     * @param <T>               The type parameter for the request body type.
     * @param <E>               The type parameter for the response body type being expected.
     * @return                  The object received from deserializing the response body into the
     *                          expected class.
     * @throws InvalidRequestStateException     Throws it if the sending of the request fails or
     *                                          if the response is not of the expected type, which
     *                                          would mean the operation failed.
     */
    public <T, E> E delete(String path, T parameters, Class<E> responseType)
        throws InvalidRequestStateException {
        // Build HTTP request
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder().uri(URI.create(host + "/" + path))
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(parameters))).build();
            String r = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return objectMapper.readValue(r, responseType);
        } catch (IOException | InterruptedException e) {
            throw new InvalidRequestStateException();
        }
    }


    /** This method converts an object from one class to another by serializing into JSON
     *      and then deserializing into the target class.
     *
     * @param from The object which is being converted.
     * @param to The {@link Class} in which the object is converted.
     * @param <T> A generic type parameter indicating the class of the inout object.
     * @param <E> A generic type parameter indicating the target class to convert to.
     * @return The converted object after serialization/deserialization, which is
     *      an instance of @param E.
     */
    public <T, E> E convertModel(T from, Class<E> to) {

        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(from), to);
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }
}
