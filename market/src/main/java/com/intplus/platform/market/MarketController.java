package com.intplus.platform.market;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarketController {

    @RequestMapping("/hi")
    public String hello(){
        return "Hi from Market!";
    }
}
