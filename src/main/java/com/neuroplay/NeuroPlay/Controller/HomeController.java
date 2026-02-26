package com.neuroplay.NeuroPlay.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller  
public class HomeController {


@GetMapping("/")
public String homePage() {
    return "index.html"; // serves the static file
}

        @GetMapping("/template")
    public String templatePage(Model model) {
        model.addAttribute("username", "NeuroPlay User");
        model.addAttribute("items", List.of(
                Map.of("title", "Dynamic Card 1", "content", "This content comes from the backend!"),
                Map.of("title", "Dynamic Card 2", "content", "You can add as many items as you want dynamically."),
                Map.of("title", "Dynamic Card 3", "content", "Thymeleaf replaces these values automatically.")
        ));
        return "template-page";
    }
}
