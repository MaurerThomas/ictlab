package hro.ictlab.dashboard.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for retrieving all the containers.
 */
@Path("/containers/")
public class GetAllContainersResource {
    private static List<URL> nodeManagerURL;
    private static FailOver failOver;

    /**
     * Creates a new URL from the system environment.
     * @throws MalformedURLException
     */
    public GetAllContainersResource() throws MalformedURLException {
        // Get the IP address from the system environment.
        if(nodeManagerURL == null) {
            //nodeManagerURL = Arrays.asList(new URL(System.getenv("NODEMANAGER")));
            // Testing URL
            nodeManagerURL = Arrays.asList(new URL("http://145.24.222.223:8080/nodemanager/api/containers"));
            failOver = new FailOver();
        }
    }

    /**
     * Responsible for getting all the containers.
     * @return HTTP status code: 200 for success or 503 for failure.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestContainers() {
        Response containers = getAllContainers();
        if (containers.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.ok().entity(containers.getEntity()).build();
        }
    }

    private Response getAllContainers() {
        return failOver.getWorkingHost(nodeManagerURL, "");
    }
}
