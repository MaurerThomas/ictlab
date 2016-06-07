import org.junit.Before;

import java.net.URL;

public class FailOverTest {
    private URL host1;

    @Before
    public void before() throws Exception {
       host1 = new URL ("http://145.24.222.223:54623");
    }

    /**
     * Method: getWorkingHost(URL url)
     */
//    @Test
//    public void testHandleUrl() throws Exception {
//        FailOver failOver = new FailOver();
//        assertEquals(Response.ok().build().getStatus(), failOver.getWorkingHost(host1).getStatus());
//    }
}
