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
 * This class is responsible for restarting a container by an ID.
 */
@Path("/containers/{id}/restart/")
public class RestartContainerResource {
    private static List<URL> nodeManagerURL;
    private static FailOver failOver;

    /**
     * Creates a new URL list from the system environment.
     * @throws MalformedURLException
     */
    public RestartContainerResource() throws MalformedURLException {
        // Get the IP address from the system environment.
        if(nodeManagerURL == null) {
            //nodeManagerURL = Arrays.asList(new URL(System.getenv("NODEMANAGER")));
            // Testing URL
            nodeManagerURL = Arrays.asList(new URL("http://145.24.222.223:8080/nodemanager/api/containers"));
            failOver = new FailOver();
        }
    }

    /**
     * Handles the restart of a container.
     * @param id The container ID.
     * @return HTTP status code: 201 for success or 503 for failure.
     */
    @GET
    public Response restartContainerById(@PathParam("id") String id) {
        Response requestToRestartContainerById = requestToRestartContainerById(id);
        if (requestToRestartContainerById.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.status(Response.Status.CREATED).entity(requestToRestartContainerById.getStatus()).build();
        }
    }

    private Response requestToRestartContainerById(String containerID) {
        return failOver.getWorkingHost(nodeManagerURL, containerID + "restart/");
    }

}
