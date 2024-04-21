package edu.iu.c322.test3.controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@CrossOrigin
public class HomeController {

    @GetMapping("/")
    public String greetings() {
        return "Welcome to the image quiz service!";
    }
}
