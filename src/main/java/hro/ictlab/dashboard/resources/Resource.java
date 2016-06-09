package hro.ictlab.dashboard.resources;

import hro.ictlab.dashboard.services.FailOver;

import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

abstract class Resource {
    private List<URL> nodeManagerURL;
    private FailOver failOver;

    Resource() throws MalformedURLException {
        constructUrl();
    }

    private void constructUrl() throws MalformedURLException {
        if (nodeManagerURL == null) {
            // nodeManagerURL = Arrays.asList(new URL(System.getenv("NODEMANAGER")));
            // Testing URL
            nodeManagerURL = Arrays.asList(new URL("http://145.24.222.223:8080/nodemanager/api"));
            failOver = new FailOver();
        }
    }

    Response getResponseFromHost(String command) {
        return failOver.getResponseFromWorkingHost(nodeManagerURL, command);
    }
}
