package hro.ictlab.dashboard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;


/**
 * This class is responsible for starting a container by an ID.
 */
@Path("/containers/{id}/start/")
public class StartContainerResource extends Resource {

    /**
     * Creates a new resource
     *
     * @throws MalformedURLException
     */
    public StartContainerResource() throws MalformedURLException {
        super();
    }

    /**
     * Handles the starting of a container.
     *
     * @param containerID The container ID.
     * @return HTTP status code: 201 for success or 503 for failure.
     */
    @GET
    public Response startContainerById(@PathParam("id") String containerID) {
        Response startContainerByIdResponse = getResponseFromHost("containers/" + containerID + "/start/");
        if (startContainerByIdResponse.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.status(Response.Status.CREATED).build();
        }
    }
}
