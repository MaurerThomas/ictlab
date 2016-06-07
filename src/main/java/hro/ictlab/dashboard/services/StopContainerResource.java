package hro.ictlab.dashboard.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for stopping a container by an ID.
 */
@Path("/containers/{id}/stop/")
public class StopContainerResource {
    private static List<URL> nodeManagerURL;
    private FailOver failOver;

    /**
     * Creates a new URL list from the system environment.
     * @throws MalformedURLException
     */
    public StopContainerResource() throws MalformedURLException {
        if(nodeManagerURL == null) {
            //nodeManagerURL = Arrays.asList(new URL(System.getenv("NODEMANAGER")));
            // Testing URL
            nodeManagerURL = Arrays.asList(new URL("http://145.24.222.223:8080/nodemanager/api/containers"));
            failOver = new FailOver();
        }
    }

    /**
     * Handles the stopping a container.
     * @param containerID The container ID.
     * @return HTTP status code: 200 for success or 503 for failure.
     */
    @GET
    public Response stopContainerById(@PathParam("id") String containerID) {
        Response stopContainerByIdResponse = requestToStopContainerById(containerID);
        if (stopContainerByIdResponse.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.ok().entity(requestToStopContainerById(containerID)).build();
        }
    }

    private Response requestToStopContainerById(String containerID) {
        return failOver.getWorkingHost(nodeManagerURL, containerID + "stop/");
    }
}
