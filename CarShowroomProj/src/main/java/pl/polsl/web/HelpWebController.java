package pl.polsl.web;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pl.polsl.Data;
import pl.polsl.Help;
import pl.polsl.ViewMode;
import pl.polsl.controller.*;
import pl.polsl.model.Dictionary;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia on 2016-06-12.
 */
@Controller
public class HelpWebController extends BaseWebController {

    @Autowired
    DictionaryController dictionaryController;
    @Autowired
    PrivilegesController privilegesController;

    private boolean flag;

    private Help help;
//
//    private List<Help> getDescription() throws Exception {
//        List<Help> helpList = new ArrayList<>();
//        Help help = new Help();
//        Resource resource = new ClassPathResource("static/help.xml");
//
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document doc = db.parse(resource.getFile());
//        doc.getDocumentElement().normalize();
//
//        NodeList nList = doc.getElementsByTagName("module");
//        for(int i = 0; i < nList.getLength(); i++) {
//            Node nNode = nList.item(i);
//            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element eElement = (Element) nNode;
//                help.setModuleName(eElement.getAttribute("id"));
//                help.setDescriptionAll(eElement.getElementsByTagName("all").item(0).getTextContent());
//                help.setDescriptionAdd(eElement.getElementsByTagName("add").item(0).getTextContent());
//                help.setDescriptionEdit(eElement.getElementsByTagName("edit").item(0).getTextContent());
//                help.setDescriptionRemove(eElement.getElementsByTagName("remove").item(0).getTextContent());
//                help.setDescriptionView(eElement.getElementsByTagName("view").item(0).getTextContent());
//                helpList.add(help);
//            }
//        }
//
//        return helpList;
//    }

    /**
     * method that allows get all details about selected module, from xml file
     * @param moduleName name of selected module
     * @return Help object that contains all necessary information
     */
    private Help getDescription(String moduleName) throws Exception {
        Help help = new Help();
        Resource resource = new ClassPathResource("static/help.xml");

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(resource.getFile());
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("module");
        for(int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE){
                Element eElement = (Element) nNode;
                if(eElement.getAttribute("id").equals(moduleName)) {
                    help.setModuleName(eElement.getAttribute("id"));
                    help.setDescriptionAll(eElement.getElementsByTagName("all").item(0).getTextContent());
                    help.setDescriptionAdd(eElement.getElementsByTagName("add").item(0).getTextContent());
                    help.setDescriptionEdit(eElement.getElementsByTagName("edit").item(0).getTextContent());
                    help.setDescriptionRemove(eElement.getElementsByTagName("remove").item(0).getTextContent());
                    help.setDescriptionView(eElement.getElementsByTagName("view").item(0).getTextContent());
                    return help;
                }
            }
        }
        return null;
    }

    /**
     * method that allows get list all available modules for current user
     * @return list all available modules for current user
     */
    private List<Dictionary> getAvaiableModules() {
        List<Dictionary> dictionaryList = dictionaryController.findAllModules();

        List<Dictionary> avaiableList = new ArrayList<>();

        for(int i = 0; i < dictionaryList.size(); i++) {
            if("Pracownicy".equals(dictionaryList.get(i).getValue()) && privilegesController.getReadPriv(Data.showroomModuleValue, Data.user)) {
                avaiableList.add(dictionaryList.get(i));
            } else if("Salony".equals(dictionaryList.get(i).getValue()) && privilegesController.getReadPriv(Data.showroomModuleValue, Data.user)) {
                avaiableList.add(dictionaryList.get(i));
            } else if(!"Pracownicy".equals(dictionaryList.get(i).getValue()) && !"Salony".equals(dictionaryList.get(i).getValue())
                    && privilegesController.getReadPriv(dictionaryList.get(i).getValue(), Data.user)) {
                avaiableList.add(dictionaryList.get(i));
            }
        }
        return avaiableList;
    }

    /**
     * GET method for return help view
     * @param model actual web model
     * @return help view
     */
    @RequestMapping(value = "/help")
    public String getCars(Model model) {
        model.addAttribute("module", getAvaiableModules());
        model.addAttribute("controlsPanelVisible", false);
        refreshMenuPrivileges(model);
        return "help";
    }

    /**
     * GET method for get information about selected module
     * @param model actual web model
     * @param moduleName name of selected module
     * @return redirect to helpDetails method
     */
    @RequestMapping(value = "/moduleDetails/{moduleName}")
    public String moduleDetails(Model model, @PathVariable("moduleName") String moduleName) {
        analisePrivileges(moduleName);
        try {
            help = getDescription(moduleName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/helpDetails";
    }

    /**
     * GET method for transfer information about selected module
     * @param model actual web model
     * @return help view
     */
    @RequestMapping(value = "/helpDetails", method = RequestMethod.GET)
    public String helpDetails(Model model){
        refreshMenuPrivileges(model);
        model.addAttribute("module", getAvaiableModules());
        model.addAttribute("controlsPanelVisible",true);
        model.addAttribute("help",help);
        model.addAttribute("insertEnabled", insertEnabled);
        model.addAttribute("updateEnabled", updateEnabled);
        model.addAttribute("deleteEnabled", deleteEnabled);
        return "help";
    }

    /**
     * GET method that allows return to previous view
     * @return redirect to help method
     */
    @RequestMapping(value="/resetView")
    public  String resetView(){
        viewMode = ViewMode.DEFAULT;
        return "redirect:/help";
    }
}