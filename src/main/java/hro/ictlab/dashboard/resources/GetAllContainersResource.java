package hro.ictlab.dashboard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;

/**
 * This class is responsible for making a request to retrieve all the containers.
 */
@Path("/containers/")
public class GetAllContainersResource extends Resource {

    /**
     * Creates a new resource
     *
     * @throws MalformedURLException
     */
    public GetAllContainersResource() throws MalformedURLException {
        super();
    }

    /**
     * Responsible for making a request to retrieve all the containers.
     *
     * @return HTTP status code: 200 for success or 503 for failure. Including all the containers in JSON format.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestContainers() {
        getResponseFromHost("nodes/hb");
        Response containers = getResponseFromHost("containers");
        if (containers.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.ok().entity(containers.getEntity()).build();
        }
    }
}
