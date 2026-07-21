package urlhttp;
/*
| Socket                            | URL                        |
| --------------------------------- | -------------------------- |
| You create both client and server | Server already exists      |
| You choose the protocol           | Usually HTTP/HTTPS         |
| Two-way communication             | Usually request/response   |
| Used for chat, games              | Used for websites and APIs |
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

/**
 * Demonstrates how to read text from a URL.
 */
public class URLReader {

    public static void main(String[] args) {

        try {

            // Create a URL object
            URL url = URI.create("https://example.com").toURL();

            // Open a stream to the URL
            //InputStreamReader converts bytes into characters
            //BufferedReader reads one line at a time
            BufferedReader reader
                    = new BufferedReader(
                            new InputStreamReader(
                                    url.openStream()));

            String line;

            System.out.println("Reading data from:");
            System.out.println(url);
            System.out.println();

            while ((line = reader.readLine()) != null) {

                System.out.println(line);

            }

            reader.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
