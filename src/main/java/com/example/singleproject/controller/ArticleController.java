package com.example.singleproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/articles")
@Controller

public class ArticleController {

    @GetMapping
    public String articles(ModelMap map){
        map.addAttribute("articles","123");
        return "articles/index";
    }
}
