package hro.ictlab.dashboard.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is responsible for stopping a container by an ID.
 */
@Path("/containers/{id}/stop/")
public class StopContainerResource {
    private static URL nodeManagerURL;
    private FailOver failOver = new FailOver();
    private String containerID;

    /**
     * Creates a new URL from the system environment.
     * @throws MalformedURLException
     */
    public StopContainerResource() throws MalformedURLException {
        //URL url = new URL("http://145.24.222.223:8080/nodemanager/api/containers/" + containerID + "/stop/");
        nodeManagerURL = new URL(System.getenv("NODEMANAGER") + containerID + "/stop/");
    }

    /**
     * Handles the stopping a container.
     * @param id The container ID.
     * @return
     */
    @GET
    public Response stopContainerById(@PathParam("id") String id) {
        containerID = id;
        Response stopContainerByIdResponse = requestToStopContainerById();
        if (stopContainerByIdResponse.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.ok().entity(requestToStopContainerById()).build();
        }
    }

    private Response requestToStopContainerById() {
        return failOver.handleUrl(nodeManagerURL);
    }
}
