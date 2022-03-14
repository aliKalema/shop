package com.phenomenal.shop.controller;

import com.phenomenal.shop.entity.Product;
import com.phenomenal.shop.entity.ProductCategory;
import com.phenomenal.shop.entity.ProductImage;
import com.phenomenal.shop.repository.ProductCategoryRepository;
import com.phenomenal.shop.repository.ProductRepository;
import com.phenomenal.shop.service.BarcodeService;
import com.phenomenal.shop.service.ProductService;
import com.phenomenal.shop.utils.SystemUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
public record ProductController(ProductService productService,
                                BarcodeService barcodeService,
                                ProductRepository productRepository,
                                ProductCategoryRepository productCategoryRepository) {
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

    @GetMapping("api/v1/admin/products")
    public ResponseEntity<Product> addProduct(@RequestParam("name")String name,
                                              @RequestParam("price")String price,
                                              @RequestParam("quantity")String quantity,
                                              @RequestParam("minQuantity")String minQuantity,
                                              @RequestParam("description")String description,
                                              @RequestParam("generate")boolean generate,
                                              @RequestParam("barcode")String barcode,
                                              @RequestParam("categoryId")int categoryId,
                                              @RequestParam("mainImg") MultipartFile mainImage,
                                              @RequestParam("additionalImgs")MultipartFile[] additionalImages) throws NoSuchAlgorithmException {
        Optional<ProductCategory> category = productCategoryRepository.findById(categoryId);
        if(category.isEmpty()){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        Product product = new Product();
        product.setName(name);
        product.setPrice(Double.parseDouble(price));
        product.setQuantity(Integer.parseInt(quantity));
        product.setMinQuantity(Integer.parseInt(minQuantity));
        product.setDescription(description);
        Set<ProductImage> productImageList = new HashSet<>();
        if(generate){
            String generatedBarcodeSerial = SystemUtils.generateRandomNumbers(12);
            barcodeService.generateBarcode(generatedBarcodeSerial);
            product.setBarCodeSerial(generatedBarcodeSerial);
        }
        else {
            Optional<Product>productExist =  productRepository.findByBarCodeSerial(barcode);
            if(productExist.isPresent()){
                return new ResponseEntity(productExist.get(),HttpStatus.CONFLICT);
            }
            barcodeService.generateBarcode(barcode);
            product.setBarCodeSerial(barcode);
        }
        if(mainImage != null) {
            ProductImage mainProductImage = productService.createImage(mainImage);
            mainProductImage.setMain(true);
            productImageList.add(mainProductImage);
        }
        if(additionalImages.length>0) {
            for(MultipartFile multipartFile : additionalImages){
                ProductImage productImage = productService.createImage(multipartFile);
                productImageList.add(productImage);
            }
        }
        if(productImageList.size()>0){
            product.setProductImages(productImageList);
        }

        return new ResponseEntity<>(product,HttpStatus.OK);
    }
}
