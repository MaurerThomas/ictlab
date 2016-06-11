package hro.ictlab.dashboard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;

/**
 * This class is responsible for making a request to delete a container by ID.
 */
@Path("/containers/{id}/delete/")
public class DeleteContainerResource extends Resource {

    /**
     * Creates a new resource
     *
     * @throws MalformedURLException
     */
    public DeleteContainerResource() throws MalformedURLException {
        super();
    }

    /**
     * Sends a request to delete a container by ID.
     *
     * @param containerID The container ID.
     * @return HTTP status code: 200 for success or 503 for failure.
     */
    @GET
    public Response deleteContainerById(@PathParam("id") String containerID) {
        Response deleteContainerByIdResponse = getResponseFromHost("containers/" + containerID + "/delete/");
        if (deleteContainerByIdResponse.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.ok().status(Response.Status.CREATED).build();
        }
    }
}
