package com.keikoshop.shop;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShopController {
    

    @RequestMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
