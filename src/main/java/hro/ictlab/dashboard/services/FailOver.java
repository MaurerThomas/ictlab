package hro.ictlab.dashboard.services;


import hro.ictlab.dashboard.exception.FailToConnectException;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is responsible for connecting to a working host.
 */
public class FailOver {
    private UrlReader urlReader = new UrlReader();
    private Logger logger = Logger.getLogger("myLogger");

    /**
     * Creates a new URL from the system environment.
     * @throws MalformedURLException
     */
    public FailOver() throws MalformedURLException {
    }

    /**
     * @param url The URL to connect to.
     * @param command The command that needs to be executed.
     * @return HTTP status code: 200 for success or 503 for failure.
     */
    public Response getWorkingHost(List<URL> url, String command)  {
        try {
            return tryToConnectToUrl(url, command);
        } catch (MalformedURLException | URISyntaxException | FailToConnectException e) {
            logConnectionError(e);
        }
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
    }

    private Response tryToConnectToUrl(List<URL> url, String command) throws FailToConnectException, MalformedURLException, URISyntaxException {
        URL workingHost = lookUpAllHosts(url);
        URL finalURL = addExtraPathToUrl(workingHost, command);
        if (workingHost != null) {
            return Response.ok().entity(urlReader.readFromUrl(finalURL)).build();
        }
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
    }

    private URL lookUpAllHosts(List<URL> hosts) throws FailToConnectException {
        for (URL host : hosts) {
            if (pingHost(host)) {
                return host;
            }
        }
        throw new FailToConnectException("All hosts are down.");
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

    private static URL addExtraPathToUrl(URL baseUrl, String command) throws URISyntaxException, MalformedURLException {
        URI uri = baseUrl.toURI();
        String newPath = uri.getPath() + '/' + command;
        URI newUri = uri.resolve(newPath);
        return newUri.toURL();
    }

    private void logConnectionError(Exception e)  {
        logger.log(Level.SEVERE, "Could not connect to URL", e);
    }
}
