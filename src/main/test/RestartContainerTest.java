import hro.ictlab.dashboard.exception.FailToConnectException;
import hro.ictlab.dashboard.resources.RestartContainerResource;
import hro.ictlab.dashboard.services.UrlReader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.hamcrest.Matchers.*;

import javax.ws.rs.core.Response;

import java.net.URL;

import static org.junit.Assert.assertEquals;

public class RestartContainerTest {

    @Test
    public void verifyResponseRestartContainer() throws Exception {
        RestartContainerResource restartContainerResource = new RestartContainerResource();
        String containerID = "3";
        assertEquals(Response.status(Response.Status.CREATED).build().getStatus(),restartContainerResource.restartContainerById(containerID).getStatus());
    }

    @Test(expected=FailToConnectException.class)
    public void verifyFailureResponseRestartContainer() throws Exception {
        URL url = new URL("http://145.24.222.223:8080/nodemanager/api/containers/null/restart");
        UrlReader.readFromUrl(url);
    }
}
