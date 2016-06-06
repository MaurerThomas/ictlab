package hro.ictlab.dashboard.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class is responsible for starting a container by an ID.
 */
@Path("/containers/{id}/start/")
public class StartContainerResource {
    private static URL nodeManagerURL;
    private FailOver failOver = new FailOver();
    private String containerID;

    /**
     * Creates a new URL from the system environment.
     * @throws MalformedURLException
     */
    public StartContainerResource() throws MalformedURLException {
        // Get the IP address from the system environment.
        //nodeManagerURL = new URL(System.getenv("NODEMANAGER") + id + "/start/");
        // Testing URL
        nodeManagerURL = new URL("http://145.24.222.223:8080/nodemanager/api/containers/" + containerID + "/start/");
    }

    /**
     * Handles the starting of a container.
     * @param id The container ID.
     * @return HTTP status code: 201 for success or 503 for failure.
     */
    @GET
    public Response startContainerById(@PathParam("id") String id) {
        containerID = id;
        Response startContainerByIdResponse = requestToStartContainerById();
        if (startContainerByIdResponse.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.status(Response.Status.CREATED).entity(startContainerByIdResponse.getStatus()).build();
        }
    }

    private Response requestToStartContainerById() {
        return failOver.handleUrl(nodeManagerURL);
    }
}
