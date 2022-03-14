package com.phenomenal.shop.controller;

import com.phenomenal.shop.service.ProductService;
import com.phenomenal.shop.service.UserService;
import com.phenomenal.shop.utils.SystemUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public record PageController(UserService userSettingsService,
                             ProductService productService) {
    @GetMapping("/login")
    public ModelAndView loginPage(){
        ModelAndView mav =  SystemUtils.createMav("login");
        return mav;
    }
    @GetMapping("/")
    public ModelAndView homePage(Authentication authentication){
        ModelAndView mav =  SystemUtils.createMav("pos");
        mav.addObject("user",userSettingsService.getUserSettings(authentication));
        mav.addObject("productCategories",productService.getAllProductCategories());
        return mav;
    }
    @GetMapping("/admin/product")
    public ModelAndView productsPage(Authentication authentication){
        ModelAndView mav =  SystemUtils.createMav("products");
        mav.addObject("user",userSettingsService.getUserSettings(authentication));
        mav.addObject("categories",productService.getAllProductCategories());
        return mav;
    }
    @GetMapping("/admin/product/new")
    public ModelAndView newProductsPage(Authentication authentication){
        ModelAndView mav =  SystemUtils.createMav("new-product");
        mav.addObject("user",userSettingsService.getUserSettings(authentication));
        mav.addObject("categories",productService.getAllProductCategories());
        return mav;
    }
}
