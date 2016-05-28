package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import pl.polsl.Data;
import pl.polsl.controller.PrivilegesController;

/**
 * Created by Kuba on 27.05.2016.
 */
public class BaseWebController {

    @Autowired
    protected PrivilegesController privilegesController;

    protected boolean insertEnabled;
    protected boolean updateEnabled;
    protected boolean deleteEnabled;

    protected void analisePrivileges(String modulename){
        insertEnabled = privilegesController.getInsertPriv(modulename, Data.user);
        updateEnabled = privilegesController.getDeletePriv(modulename, Data.user);
        deleteEnabled = privilegesController.getUpdatePriv(modulename, Data.user);
    }

}


