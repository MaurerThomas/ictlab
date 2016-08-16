import hro.ictlab.dashboard.exception.FailToConnectException;
import hro.ictlab.dashboard.resources.CreateContainerResource;
import hro.ictlab.dashboard.resources.StartContainerResource;
import hro.ictlab.dashboard.services.UrlReader;
import org.json.JSONObject;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class CreateContainerTest {

    @Test
    public void verifyResponseCreateContainer() throws Exception {
        CreateContainerResource createContainerResource = new CreateContainerResource();
        JSONObject container = new JSONObject();
        container.put("containerName", "testContainer");
        container.put("node", "145.24.222.140");
        container.put("baseImage", "jenkins");
        container.put("hostPort", "95");
        container.put("containerPort", "8085");
        assertEquals(Response.status(Response.Status.CREATED).build().getStatus(),createContainerResource.createContainer(container.toString()).getStatus());
    }

}
