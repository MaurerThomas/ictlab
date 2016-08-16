import hro.ictlab.dashboard.exception.FailToConnectException;
import hro.ictlab.dashboard.resources.RestartContainerResource;
import hro.ictlab.dashboard.resources.StartContainerResource;
import hro.ictlab.dashboard.services.UrlReader;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class StartContainerTest {

    @Test
    public void verifyResponseStartContainer() throws Exception {
        StartContainerResource startContainerResource = new StartContainerResource();
        String containerID = "3";
        assertEquals(Response.status(Response.Status.CREATED).build().getStatus(),startContainerResource.startContainerById(containerID).getStatus());
    }

    @Test(expected=FailToConnectException.class)
    public void verifyFailureResponseStartContainer() throws Exception {
        URL url = new URL("http://145.24.222.223:8080/nodemanager/api/containers/null/start");
        UrlReader.readFromUrl(url);
    }
}
