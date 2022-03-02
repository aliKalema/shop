package com.phenomenal.shop.utils;

import org.springframework.web.servlet.ModelAndView;

public record SystemUtils(){
    public static ModelAndView createMav(String viewName){
        ModelAndView mav  = new ModelAndView();
        mav.setViewName(viewName);return mav;
    }
}
