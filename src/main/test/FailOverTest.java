
import org.junit.Before;
import org.junit.Test;

import services.FailOver;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;


import java.net.URL;

public class FailOverTest {
    private URL host1;

    @Before
    public void before() throws Exception {
       host1 = new URL ("http://145.24.222.223:54623");
    }

    /**
     *
     * Method: handleUrl(URL url)
     *
     */
    @Test
    public void testHandleUrl() throws Exception {
        FailOver failOver = new FailOver();
        assertEquals(Response.ok().build().getStatus(), failOver.handleUrl(host1).getStatus());
    }
}
