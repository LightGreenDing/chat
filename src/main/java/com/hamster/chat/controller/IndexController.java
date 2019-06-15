package com.hamster.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "html/index2.html";
    }

    @GetMapping("/chat1")
    public String chat1() {
        return "html/chat1.html";
    }

    @GetMapping("/chat2")

    public String chat2() {
        return "html/chat2.html";
    }

    @GetMapping("/login")
    @ResponseBody
    public String login() {
        return "chat success";
    }
}
