package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;

@Path("/docker")
public class CreateContainerResource {
    private FailOver failOver = new FailOver();

    @GET
    @Path("/containers/")
    public Response createContainer(@PathParam("id") String id) throws IOException {
        if (requestToCreateContainer(id).getStatus() != 200) {
            return Response.noContent().build();
        } else {
            return Response.ok().entity(requestToCreateContainer(id)).build();
        }
    }

    private Response requestToCreateContainer(String id) throws IOException {
        URL url = new URL(System.getenv("NODEMANAGER_START") + id + "-stop/");
        //URL url = new URL("http://145.24.222.223:54623/api/Command/" + id + "-start/");
        return failOver.handleUrl(url);
    }
}
