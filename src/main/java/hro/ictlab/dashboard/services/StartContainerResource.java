package hro.ictlab.dashboard.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Path("/containers/{id}/start/")
public class StartContainerResource {
    private FailOver failOver = new FailOver();

    public StartContainerResource() throws MalformedURLException {
    }

    @GET
    public Response startContainerById(@PathParam("id") String id) throws IOException {
        Response response = requestToStartContainerById(id);
        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.ok().entity(requestToStartContainerById(id)).build();
        }
    }

    private Response requestToStartContainerById(String id) throws IOException {
        // Get the IP address from the system environment.
        //URL url = new URL(System.getenv("NODEMANAGER_START") + id + "-start/");
        URL url = new URL("http://145.24.222.223:54623/api/Command/" + id + "-start/");
        return failOver.handleUrl(url);
    }
}
