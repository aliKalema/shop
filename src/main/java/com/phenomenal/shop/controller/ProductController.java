package com.phenomenal.shop.controller;

import com.phenomenal.shop.entity.Product;
import com.phenomenal.shop.entity.ProductCategory;
import com.phenomenal.shop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public record ProductController(ProductService productService) {
    @PostMapping("/api/v1/admin/products")
    public Product addProduct(){
        return null;
    }

    @PostMapping("/api/v1/admin/categories")
    public ResponseEntity<ProductCategory> addCategory(@RequestParam("name")String name){
        return productService.addProductCategory(name);
    }

    @GetMapping("api/v1/admin/categories/product/{categoryId}/{productId}")
    public ResponseEntity<ProductCategory> addProcuctToCategory(@PathVariable("categoryId")int categoryId,@PathVariable("productId")int productId){
        return productService.addProductToProductCategory(categoryId,productId);
    }
}
