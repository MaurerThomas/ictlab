package hro.ictlab.dashboard.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Path("/containers/")
public class GetAllContainersResource {
    private FailOver failOver = new FailOver();

    public GetAllContainersResource() throws MalformedURLException {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestContainers() throws IOException {
        Response containers = getAllContainers();
        if (containers.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.ok().entity(containers.getEntity()).build();
        }
    }

    private Response getAllContainers() throws IOException {
        //URL url = new URL(System.getenv("NODEMANAGER_ALL"));
        URL url = new URL("http://145.24.222.223:8080/nodemanager/api/containers");
        return failOver.handleUrl(url);
    }
}
