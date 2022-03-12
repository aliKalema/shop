package com.phenomenal.shop.service;

import com.phenomenal.shop.entity.Product;
import com.phenomenal.shop.entity.ProductCategory;
import com.phenomenal.shop.repository.ProductCategoryRepository;
import com.phenomenal.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public List<Product> getAllProducts(){
        List<Product>products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    public ResponseEntity<ProductCategory> addProductCategory(String name){
        ProductCategory category = new ProductCategory();
        category.setName(name);
        productCategoryRepository.save(category);
        return new ResponseEntity(category,HttpStatus.OK);
    }

    public ResponseEntity<ProductCategory> addProductToProductCategory(int productCategoryId, int productId){
        Optional<ProductCategory> productCategory = productCategoryRepository.findById(productCategoryId);
        Optional<Product>product =  productRepository.findById(productId);
        if(productCategory.isEmpty() | product.isEmpty()){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        productCategory.get().addProduct(product.get());
        productCategoryRepository.save(productCategory.get());
        return new ResponseEntity(productCategory.get(),HttpStatus.OK);
    }
}
