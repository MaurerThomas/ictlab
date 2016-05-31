package hro.ictlab.dashboard.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Path("/containers/{id}/stop/")
public class StopContainerResource {
    private FailOver failOver = new FailOver();

    public StopContainerResource() throws MalformedURLException {
    }

    @GET
    public Response stopContainerById(@PathParam("id") String id) throws IOException {
        Response stopContainerByIdResponse = requestToStopContainerById(id);
        if (stopContainerByIdResponse.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.ok().entity(requestToStopContainerById(id)).build();
        }
    }

    private Response requestToStopContainerById(String id) throws IOException {
        URL url = new URL(System.getenv("NODEMANAGER") + id + "/stop/");
        //URL url = new URL("http://145.24.222.223:8080/nodemanager/api/containers/" + id + "/stop/");
        return failOver.handleUrl(url);
    }
}
