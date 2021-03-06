package hro.ictlab.dashboard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;

/**
 * This class is responsible for making a request to restart a container by an ID.
 */
@Path("/containers/{id}/restart/")
public class RestartContainerResource extends Resource {

    /**
     * Creates a new resource
     *
     * @throws MalformedURLException
     */
    public RestartContainerResource() throws MalformedURLException {
        super();
    }

    /**
     * Responisble for making a request to top a container by an ID.
     *
     * @param containerID The container ID.
     * @return HTTP status code: 201 for success or 503 for failure.
     */
    @GET
    public Response restartContainerById(@PathParam("id") String containerID) {
        Response requestToRestartContainerById = getResponseFromHost("containers/" + containerID + "/restart/");
        if (requestToRestartContainerById.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.status(Response.Status.CREATED).build();
        }
    }
}
