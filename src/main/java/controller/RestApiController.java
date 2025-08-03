package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestApiController {

    @GetMapping("/home")
    public String home(){
        return "<h1>home</h1>";
    }
}
