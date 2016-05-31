package hro.ictlab.dashboard.services;


import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.*;

public class FailOver {
    private UrlReader urlReader = new UrlReader();
    //All hosts
    //URL[] allHosts = new URL[]{new URL(System.getenv("NODEMANAGER_START"))};
    private URL[] allHosts;

    public FailOver() throws MalformedURLException {
        this.allHosts = new URL[]{new URL("http://145.24.222.223:8080/nodemanager/api/containers")};
    }

    public Response handleUrl(URL url) throws IOException {
        if (pingHost(url)) {
            return Response.ok().entity(urlReader.readFromUrl(url)).build();
        } else {
            for (URL newWorkingHost : allHosts) {
                if (pingHost(newWorkingHost)) {
                    return Response.ok().entity(urlReader.readFromUrl(newWorkingHost)).build();
                }
            }
        }
        return Response.noContent().build();
    }

    private static boolean pingHost(URL host) {
        try{
            final URLConnection connection = new URL(host.toString()).openConnection();
            connection.connect();
            return true;
        } catch(final MalformedURLException e){
            throw new IllegalStateException("Error: Wrong URL " + host, e);
        } catch(final IOException e){
            return false;
        }
    }
}
