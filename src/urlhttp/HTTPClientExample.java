package urlhttp;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * HTTPClientExample
 *
 * Demonstrates how to connect to a URL using Java's modern HttpClient API and
 * read the response returned by the server.
 *
 * Learning Objectives 1. Create an HttpClient. 2. Create an HTTP GET request.
 * 3. Send the request to a web server. 4. Read the server's response.
 */
public class HTTPClientExample {

    public static void main(String[] args) {

        try {

            // ----------------------------------------------------
            // Create an HTTP client
            // ----------------------------------------------------
            HttpClient client = HttpClient.newHttpClient();

            // ----------------------------------------------------
            // Create an HTTP GET request
            // ----------------------------------------------------
            HttpRequest request
                    = HttpRequest.newBuilder()
                            .uri(URI.create("https://example.com"))
                            .GET()
                            .build();

            System.out.println("Sending request...");
            System.out.println();

            // ----------------------------------------------------
            // Send the request and receive the response
            // ----------------------------------------------------
            HttpResponse<String> response
                    = client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString());// The server sends bytes. BodyHandlers.ofString() converts those bytes into a Java String that we can print or process.

            // ----------------------------------------------------
            // Display information returned by the server
            // ----------------------------------------------------
            System.out.println("Status Code: "
                    + response.statusCode());

            System.out.println();

            System.out.println("Response Headers:");
            response.headers()
                    .map()
                    .forEach((key, value)
                            -> System.out.println(key + " : " + value));

            System.out.println();

            System.out.println("Response Body:");
            System.out.println("--------------------------------------");
            System.out.println(response.body());

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
