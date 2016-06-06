package hro.ictlab.dashboard.services;


import hro.ictlab.dashboard.exception.FailToConnectException;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is responsible for connecting to a working host.
 */
public class FailOver {
    private UrlReader urlReader = new UrlReader();
    //All hosts
    //URL[] allHosts = new URL[]{new URL(System.getenv("NODEMANAGER_START"))};
    private URL[] allHosts;
    private Logger logger = Logger.getLogger("myLogger");

    /**
     * Creates a new URL from the system environment.
     * @throws MalformedURLException
     */
    public FailOver() throws MalformedURLException {
        this.allHosts = new URL[]{new URL("http://145.24.222.223:8080/nodemanager/api/containers")};
    }

    /**
     * 
     * @param url The URL to connect to.
     * @return
     */
    public Response connectToUrl(URL url) {
        try {
            handleUrl(url);
        } catch (FailToConnectException e) {
            logConnetionError(e);
        }
        return Response.noContent().build();
    }

    private static boolean pingHost(URL host) {
        try {
            URLConnection connection = host.openConnection();
            connection.connect();
            return true;
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Error: Wrong URL " + host, e);
        } catch (IOException e) {
            return false;
        }
    }

    private Response handleUrl(URL url) throws FailToConnectException {
        if (pingHost(url)) {
            return Response.ok().entity(urlReader.readFromUrl(url)).build();
        } else {
            return responseFromBackUpHost();
        }
    }

    private Response responseFromBackUpHost() {
        for (URL newWorkingHost : allHosts) {
            try {
                if (pingHost(newWorkingHost)) {
                    return Response.ok().entity(urlReader.readFromUrl(newWorkingHost)).build();
                }
            } catch (FailToConnectException e) {
                logConnetionError(e);
            }
        }
        return Response.noContent().build();
    }

    private void logConnetionError(Exception e) {
        logger.log(Level.SEVERE, "Could not connect to URL", e);
    }
}
