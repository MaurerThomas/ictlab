package hro.ictlab.dashboard.services;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * All JAX-RS component classes that are either manually registered or found during scanning are automatically added to
 * the set of classes that are returned by getClasses. For example, the following application class that extends from
 * ResourceConfig scans during deployment for JAX-RS components in packages for this class.
 */
@ApplicationPath("api")
public class MyResourceConfig extends ResourceConfig {
    public MyResourceConfig() {
        packages(MyResourceConfig.class.getPackage().getName());
    }
}
