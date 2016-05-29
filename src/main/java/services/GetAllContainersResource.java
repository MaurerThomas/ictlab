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
    private FailOver failOver = new FailOver();

    @GET
    @Path("/containers/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestContainers() throws IOException {

        if (getAllContainers().getStatus() != 200) {
            return Response.noContent().build();
        } else {
            return Response.ok().entity(getAllContainers()).build();
        }
    }

    private Response getAllContainers() throws IOException {
        //URL url = new URL(System.getenv("NODEMANAGER_ALL"));
        URL url = new URL("http://145.24.222.223:54623/api/containers");
        return failOver.handleUrl(url);
    }
}
