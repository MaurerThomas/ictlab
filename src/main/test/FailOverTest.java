import hro.ictlab.dashboard.services.FailOver;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class FailOverTest {
    private List<URL> nodeManagerURL;
    private FailOver failOver;

    @Before
    public void before() throws Exception {
        failOver = new FailOver();
        nodeManagerURL = Arrays.asList(new URL("http://145.24.222.224:8080/nodemanager/api/containers"),new URL("http://145.24.222.223:8080/nodemanager/api/containers"));
    }

    @Test
    public void failOverTest() {
        assertEquals(Response.status(Response.Status.OK).build().getStatus(), failOver.getResponseFromWorkingHost(nodeManagerURL,"").getStatus());
    }
}
