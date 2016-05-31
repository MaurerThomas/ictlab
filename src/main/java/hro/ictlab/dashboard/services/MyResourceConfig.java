package hro.ictlab.dashboard.services;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
    public class MyResourceConfig extends ResourceConfig {
        public MyResourceConfig() {
            packages(MyResourceConfig.class.getPackage().getName());
        }
}
