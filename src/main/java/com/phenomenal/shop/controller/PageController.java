package com.phenomenal.shop.controller;

import com.phenomenal.shop.entity.Product;
import com.phenomenal.shop.service.ProductService;
import com.phenomenal.shop.service.CustomerService;
import com.phenomenal.shop.service.SalesService;
import com.phenomenal.shop.service.UserService;
import com.phenomenal.shop.utils.SystemUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
public record PageController(UserService userSettingsService,
                             ProductService productService,
                             SalesService salesService,
                             CustomerService customerService) {
    @GetMapping("/login")
    public ModelAndView loginPage(){
        ModelAndView mav =  SystemUtils.createMav("login");
        return mav;
    }

    @GetMapping("/")
    public ModelAndView homePage(Authentication authentication){
        ModelAndView mav =  SystemUtils.createMav("pos");
        mav.addObject("user",userSettingsService.getUserSettings(authentication));
        mav.addObject("categories",productService.getCategoriesWithProduct());
        mav.addObject("customers",customerService.getCustomers());
        return mav;
    }

    @GetMapping("/admin/product")
    public ModelAndView productsPage(Authentication authentication){
        ModelAndView mav =  SystemUtils.createMav("products");
        mav.addObject("user",userSettingsService.getUserSettings(authentication));
        mav.addObject("products",productService.getAllProducts());
        mav.addObject("categories",productService.getAllProductCategories());
        return mav;
    }

    @GetMapping("/admin/sale")
    public ModelAndView salesPage(Authentication authentication){
        ModelAndView mav =  SystemUtils.createMav("sales");
        mav.addObject("user",userSettingsService.getUserSettings(authentication));
        mav.addObject("sales",salesService.getAllSales());
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

    @GetMapping("/admin/product/{id}")
    public ModelAndView ProductsDetailsPage(Authentication authentication,@PathVariable("id")int id){
        ModelAndView mav =  SystemUtils.createMav("product_page");
        mav.addObject("user",userSettingsService.getUserSettings(authentication));
        Optional<Product> product = productService.findById(id);
        if(product.isEmpty()){
            return new ModelAndView("error/404");
        }
        productService.setProductStructure(product.get(),true);
        mav.addObject("product",product.get());
        return mav;
    }

    @GetMapping("/admin/product/edit/{id}")
    public ModelAndView ProductEditPage(Authentication authentication,@PathVariable("id")int id){
        ModelAndView mav =  SystemUtils.createMav("edit_product");
        mav.addObject("user",userSettingsService.getUserSettings(authentication));
        mav.addObject("categories",productService.getAllProductCategories());
        Optional<Product> product = productService.findById(id);
        if(product.isEmpty()){
            return new ModelAndView("error/404");
        }
        productService.setProductStructure(product.get(),false);
        mav.addObject("product",product.get());
        return mav;
    }
}
