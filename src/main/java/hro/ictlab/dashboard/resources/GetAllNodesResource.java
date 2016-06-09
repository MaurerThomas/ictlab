package hro.ictlab.dashboard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;

@Path("/nodes/")
public class GetAllNodesResource extends Resource {

    /**
     * Creates a new resource
     *
     * @throws MalformedURLException
     */
    public GetAllNodesResource() throws MalformedURLException {
        super();
    }

    /**
     * Responsible for getting all the nodes.
     *
     * @return HTTP status code: 200 for success or 503 for failure.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestNodes() {
        Response nodes = getResponseFromHost("nodes");
        if (nodes.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.ok().entity(nodes.getEntity()).build();
        }
    }


}
