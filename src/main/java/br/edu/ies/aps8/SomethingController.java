package br.edu.ies.aps8;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomethingController {

    @GetMapping
    public String hello() {
        return "\"OK\"";
    }

}
