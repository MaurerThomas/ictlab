package hro.ictlab.dashboard.client;

import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;


public class Client {
    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();

        javax.ws.rs.client.Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(getBaseURI());

        String response = target.path("docker").
                path("/containers/{id}/start/").
                request().
                accept(MediaType.APPLICATION_JSON).
                get(Response.class)
                .toString();

        String plainAnswer =
                target.path("rest").path("hello").request().accept(MediaType.TEXT_PLAIN).get(String.class);


        System.out.println(response);
        System.out.println(plainAnswer);
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/ictlab/resources").build();
    }
}





