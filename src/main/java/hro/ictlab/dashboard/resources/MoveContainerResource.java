package hro.ictlab.dashboard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;

/**
 * This class is responsible for making a request to move a container to a different host.
 */
@Path("/containers/{id}/move/{node}/")
public class MoveContainerResource extends Resource {

    /**
     * Creates a new resource
     *
     * @throws MalformedURLException
     */
    public MoveContainerResource() throws MalformedURLException {
        super();
    }

    /**
     * Responisble for making a request to top a container by an ID.
     *
     * @param containerID The container ID.
     * @param node The node to move to.
     * @return HTTP status code: 201 for success or 503 for failure.
     */
    @GET
    public Response moveContainerById(@PathParam("id") String containerID, @PathParam("node") String node) {
        Response requestToMoveContainerById = getResponseFromHost("containers/" + containerID + "/move/" + node);
        if (requestToMoveContainerById.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.status(Response.Status.CREATED).build();
        }
    }
}
