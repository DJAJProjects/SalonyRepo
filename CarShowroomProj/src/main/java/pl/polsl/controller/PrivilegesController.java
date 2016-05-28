package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.*;
import pl.polsl.repository.InvoiceRepository;
import pl.polsl.repository.PrivilegesRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Component
@Path("/privileges")
@Produces(MediaType.APPLICATION_JSON)

public class PrivilegesController {
    @Autowired
    private PrivilegesRepository privilegesRepository;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Privileges> findAll() {
        return Lists.newArrayList(privilegesRepository.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Privileges findOne(int id){
        return privilegesRepository.findOne(id);}

    public boolean getInsertPriv(String moduleName, Worker worker){
        List<WorkersPrivileges> result = privilegesRepository.getInsertPriv(moduleName, worker);
        if(result == null || result.size() == 0) return false;
        else return true;
    }

    public boolean getDeletePriv(String moduleName, Worker worker){
        List<WorkersPrivileges> result = privilegesRepository.getDeletePriv(moduleName, worker);
        if(result == null || result.size() == 0) return false;
        else return true;
    }

    public boolean getUpdatePriv(String moduleName, Worker worker){
        List<WorkersPrivileges> result = privilegesRepository.getUpdatePriv(moduleName, worker);
        if(result == null || result.size() == 0) return false;
        else return true;
    }

}
