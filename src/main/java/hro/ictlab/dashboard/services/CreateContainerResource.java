package hro.ictlab.dashboard.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is responsible for creating a new container.
 */
@Path("/docker")
public class CreateContainerResource {
    private static URL nodeManagerURL;
    private FailOver failOver = new FailOver();
    private String containerID;

    /**
     * Creates a new URL from the system environment.
     * @throws MalformedURLException
     */
    public CreateContainerResource() throws MalformedURLException {
        nodeManagerURL = new URL(System.getenv("NODEMANAGER_START") + containerID + "-stop/");
    }

    @GET
    @Path("/containers/")
    public Response createContainer(@PathParam("id") String id) {
        if (requestToCreateContainer(id).getStatus() != 200) {
            return Response.noContent().build();
        } else {
            return Response.ok().entity(requestToCreateContainer(id)).build();
        }
    }

    private Response requestToCreateContainer(String id) {
        //URL url = new URL("http://145.24.222.223:54623/api/Command/" + id + "-start/");
        return failOver.handleUrl(nodeManagerURL);
    }
}
