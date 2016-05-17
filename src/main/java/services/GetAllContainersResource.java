package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;

@Path("/docker")
public class GetAllContainersResource {
    private UrlReader urlReader = new UrlReader();

    @GET
    @Path("/containers/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestContainers() throws IOException {
        if (getAllContainers().isEmpty()) {
            return Response.noContent().build();
        } else {
            return Response.ok().entity(getAllContainers()).build();
        }
    }

    private String getAllContainers() throws IOException {
        URL url = new URL("http://localhost.nl");
        return urlReader.readFromUrl(url);
    }
}
