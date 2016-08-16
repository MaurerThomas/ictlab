package hro.ictlab.dashboard.resources;

import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;

/**
 * This class is responsible for requesting to create a new container.
 */
@Path("/containers/")
public class CreateContainerResource extends Resource {

    /**
     * Creates a new resource
     *
     * @throws MalformedURLException
     */
    public CreateContainerResource() throws MalformedURLException {
        super();
    }

    /**
     * Request to create a new container.
     * @param jsonRequest A JSON object containting information about a new container that looks like this:
     * <pre>
     * {
     *  "containerName": containerName,
     *  "node": node,
     *  "baseImage": baseImage,
     *  "hostPort": hostPort,
     *  "containerPort": containerPort
     * }
     * </pre>
     * @return HTTP status code: 201 for success or 503 for failure.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createContainer(String jsonRequest) {
        Response checkIfHostIsAvailable = getResponseFromHost("");
        if (checkIfHostIsAvailable.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://145.24.222.223:8080/nodemanager/api/containers");
            //WebTarget target = client.target(System.getenv("NODEMANAGER") + "/containers");
            Response requestToCreateContainer = target.request().post(Entity.entity(jsonRequest, MediaType.APPLICATION_JSON));
            if (requestToCreateContainer.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
            }
            return Response.status(Response.Status.CREATED).build();
        }
    }
}
