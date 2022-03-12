package com.phenomenal.shop.utils;

import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

public record SystemUtils(){
    public static ModelAndView createMav(String viewName){
        ModelAndView mav  = new ModelAndView();
        mav.setViewName(viewName);return mav;
    }
    public static String generateRandomNumbers(int length){
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return new String(digits);
    }
}
