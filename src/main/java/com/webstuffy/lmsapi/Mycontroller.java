package com.webstuffy.lmsapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Mycontroller {

    @RequestMapping("tst")
    String hi() {
        return "hiiii2";
    }
}
