package hro.ictlab.dashboard.services;

import org.json.JSONObject;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for creating a new container.
 */
@Path("/containers/")
public class CreateContainerResource {
    private static List<URL> nodeManagerURL;
    private FailOver failOver;

    /**
     * Creates a new URL list from the system environment.
     * @throws MalformedURLException
     */
    public CreateContainerResource() throws MalformedURLException {
        // Get the IP address from the system environment.
        if(nodeManagerURL == null) {
            // nodeManagerURL = Arrays.asList(new URL(System.getenv("NODEMANAGER")));
            // Testing URL
            nodeManagerURL = Arrays.asList(new URL("http://145.24.222.223:8080/nodemanager/api/containers"));
            failOver = new FailOver();
        }
    }

    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    public Response createContainer(@FormParam("containerName") String containerName, @FormParam("node") String node, @FormParam("baseImage") String baseImage, @FormParam("hostPort") String hostPort, @FormParam("containerPort") String containerPort) {
        JSONObject containerObject = new JSONObject();
        containerObject.put("containerName", containerName);
        containerObject.put("node", node);
        containerObject.put("baseImage", baseImage);
        containerObject.put("hostPort", hostPort);
        containerObject.put("containerPort", containerPort);

        Response requestToCreateContainer = requestToCreateContainer(containerObject);

        if (requestToCreateContainer.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            
            return Response.status(Response.Status.CREATED).build();
        }


    }

    private Response requestToCreateContainer(JSONObject newContainer) {
        return failOver.getWorkingHost(nodeManagerURL, "");
    }
}
