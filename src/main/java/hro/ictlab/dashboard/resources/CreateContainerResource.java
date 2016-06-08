package hro.ictlab.dashboard.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;

/**
 * This class is responsible for creating a new container.
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
     * Sends information to the backend for creating a new container.
     *
     * @param containerName The container name.
     * @param node          The node it must be placed on.
     * @param baseImage     The base image for the container.
     * @param hostPort      The port of the host.
     * @param containerPort The port of the container.
     * @return HTTP status code: 201 for success or 503 for failure.
     */
    @POST
    public Response createContainer(@FormParam("containerName") String containerName, @FormParam("node") String node, @FormParam("baseImage") String baseImage, @FormParam("hostPort") String hostPort, @FormParam("containerPort") String containerPort) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://145.24.222.223:8080/nodemanager/api/containers");
        Form form = new Form();
        form.param("containerName", containerName);
        form.param("node", node);
        form.param("baseImage", baseImage);
        form.param("hostPort", hostPort);
        form.param("containerPort", containerPort);

        Response response = target.request().post(Entity.entity(form, MediaType.APPLICATION_JSON));
        System.out.println(response);

        Response requestToCreateContainer = getResponseFromHost("");

        if (requestToCreateContainer.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        } else {
            return Response.status(Response.Status.CREATED).build();
        }
    }


}
