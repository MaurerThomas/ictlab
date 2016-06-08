package hro.ictlab.dashboard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;

/**
 * This class is responsible for stopping a container by an ID.
 */
@Path("/containers/{id}/stop/")
public class StopContainerResource extends Resource {

    /**
     * Creates a new resource
     *
     * @throws MalformedURLException
     */
    public StopContainerResource() throws MalformedURLException {
        super();
    }

    /**
     * Handles the stopping a container.
     *
     * @param containerID The container ID.
     * @return HTTP status code: 200 for success or 503 for failure.
     */
    @GET
    public Response stopContainerById(@PathParam("id") String containerID) {
        Response stopContainerByIdResponse = getResponseFromHost(containerID + "/stop/");
        if (stopContainerByIdResponse.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.ok().entity(getResponseFromHost(containerID)).build();
        }
    }
}
