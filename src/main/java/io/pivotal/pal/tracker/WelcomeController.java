package io.pivotal.pal.tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String sayHello() {
        return "hello";
    }
}




//cf login -u thomas.j.cramer@accenture.com
//
//https://api.sys.pikes.pal.pivotal.io
//
// 7ueddbe9