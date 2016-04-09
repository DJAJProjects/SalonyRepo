package pl.polsl;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import pl.polsl.controller.CarsController;

import javax.ws.rs.ApplicationPath;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig(){
        register(CarsController.class);
    }
}
