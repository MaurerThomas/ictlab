package hro.ictlab.dashboard.services;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
        Response startContainerByIdResponse = requestToStartContainerById(id);
        if (startContainerByIdResponse.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.status(Response.Status.CREATED).entity(startContainerByIdResponse.getStatus()).build();
        }
    }

    private Response requestToStartContainerById(String id) throws IOException {
        // Get the IP address from the system environment.
        //URL url = new URL(System.getenv("NODEMANAGER") + id + "/start/");
        // Testing URL
        URL url = new URL("http://145.24.222.223:8080/nodemanager/api/containers/" + id + "/start/");
        return failOver.handleUrl(url);
    }
}
