package services;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;

@Path("/docker")
public class StartContainerResource {
    private UrlReader urlReader = new UrlReader();

    @POST
    @Path("/containers/{id}/start")
    public Response startContainerById(@PathParam("id") String id) throws IOException {
        if (requestToStartContainerById(id).isEmpty()) {
            return Response.noContent().build();
        } else {
            return Response.ok().entity(requestToStartContainerById(id)).build();
        }
    }

    private String requestToStartContainerById(String id) throws IOException {
        URL url = new URL("http://localhost.nl/" + id );
        return urlReader.readFromUrl(url);
    }
}
