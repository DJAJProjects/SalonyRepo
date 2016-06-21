package pl.polsl;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import pl.polsl.controller.*;

import javax.ws.rs.ApplicationPath;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig(){
        register(AccessoriesController.class);
        register(CarsController.class);
        register(ContractorsController.class);
        register(ContractsController.class);
        register(DictionaryController.class);
        register(InvoiceController.class);
        register(PrivilegesController.class);
        register(PromotionsController.class);
        register(ReportsController.class);
        register(ServicesController.class);
        register(ShowroomsController.class);
        register(WorkersController.class);
        register(WorkersPrivilegesController.class);

    }
}
