package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;

@Path("/docker")
public class StartContainerResource {
    private FailOver failOver = new FailOver();

    @GET
    @Path("/containers/{id}/start/")
    public Response startContainerById(@PathParam("id") String id) throws IOException {
        if (requestToStartContainerById(id).getStatus() != 200) {
            return Response.noContent().build();
        } else {
            return Response.ok().entity(requestToStartContainerById(id)).build();
        }
    }

    private Response requestToStartContainerById(String id) throws IOException {
        //URL url = new URL(System.getenv("NODEMANAGER_START") + id + "-start/");
        URL url = new URL("http://145.24.222.223:54623/api/Command/" + id + "-start/");
        return failOver.handleUrl(url);
    }
}
