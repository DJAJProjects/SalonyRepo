package pl.polsl.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by Dominika Błasiak on 2016-04-09.
 */
@Controller
public class MainController {
    @RequestMapping(value = "/")
    public String home(){
        return "index";
    }
}
