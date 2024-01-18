package server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"","/","index"})
    public String startupPage () {
        return "redirect:swagger-ui/";
    }
}
