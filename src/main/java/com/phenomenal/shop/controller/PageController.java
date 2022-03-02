package com.phenomenal.shop.controller;

import com.phenomenal.shop.utils.SystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public record PageController() {
    @GetMapping("/")
    public ModelAndView homePage(){
        ModelAndView mav =  SystemUtils.createMav("pos");
        return mav;
    }
    @GetMapping("/login")
    public ModelAndView loginPage(){
        ModelAndView mav =  SystemUtils.createMav("login");
        return mav;
    }

}
