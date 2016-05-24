import com.sun.jersey.test.framework.JerseyTest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public class SimpleTest extends JerseyTest {
    @Path("hello")
    public static class HelloResource {
        @GET
        public String getHello() {
            return "Hello World!";
        }
    }

//    @Override
//    protected Application configure() {
//        return new ResourceConfig(MyResourceConfig.class);
//    }

//    @Test
//    public void test() {
//        final String hello = target("hello").request().get(String.class);
//        assertEquals("Hello World!", hello);
//    }
}
