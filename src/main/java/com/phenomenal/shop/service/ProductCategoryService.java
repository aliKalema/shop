package com.phenomenal.shop.service;

import com.phenomenal.shop.entity.ProductCategory;
import com.phenomenal.shop.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCategoryService {
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getAllProductCategories() {
        List<ProductCategory>categories = new ArrayList<>();
        productCategoryRepository.findAll().forEach(categories::add);
        System.out.println(categories.size());
        return categories;
    }
}
