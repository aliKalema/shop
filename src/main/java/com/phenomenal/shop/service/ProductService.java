package com.phenomenal.shop.service;

import com.phenomenal.shop.entity.Product;
import com.phenomenal.shop.entity.ProductCategory;
import com.phenomenal.shop.entity.ProductImage;
import com.phenomenal.shop.repository.ProductCategoryRepository;
import com.phenomenal.shop.repository.ProductRepository;
import com.phenomenal.shop.service.upload.ProductFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductFileServiceImpl productFileServiceImpl;

    public List<Product> getAllProducts(){
        List<Product>products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    public List<ProductCategory> getAllProductCategories(){
        List<ProductCategory>categories = new ArrayList<>();
        productCategoryRepository.findAll().forEach(categories::add);
        return categories;
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

    public ProductImage createImage(MultipartFile image) throws NoSuchAlgorithmException {
        String imageName = productFileServiceImpl.addFile(image);
        ProductImage productImage = new ProductImage();
        productImage.setName(imageName);
        productImage.setMimeType(image.getContentType());
        productImage.setSize(image.getSize());
        return productImage;
    }

}
