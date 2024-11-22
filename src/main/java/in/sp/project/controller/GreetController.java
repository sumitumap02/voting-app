package in.sp.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetController {

    @GetMapping
    public String createGreetMessage() {
        return "index";
    }

}
