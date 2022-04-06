package com.phenomenal.shop.controller;

import com.phenomenal.shop.entity.Product;
import com.phenomenal.shop.entity.ProductCategory;
import com.phenomenal.shop.entity.ProductImage;
import com.phenomenal.shop.repository.ProductCategoryRepository;
import com.phenomenal.shop.repository.ProductRepository;
import com.phenomenal.shop.service.BarcodeService;
import com.phenomenal.shop.utils.SystemUtils;
import com.phenomenal.shop.service.ProductService;
import com.phenomenal.shop.configuration.ProductStorageProperty;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
public record ProductController(ProductService productService,
                                BarcodeService barcodeService,
                                ProductRepository productRepository,
                                ProductStorageProperty productStorageProperty,
                                ProductCategoryRepository productCategoryRepository) {
    @PostMapping("/api/v1/admin/categories")
    public ResponseEntity<ProductCategory> addCategory(@RequestParam("name")String name){
        return productService.addProductCategory(name);
    }

    @GetMapping("api/v1/admin/categories/product/{categoryId}/{productId}")
    public ResponseEntity<ProductCategory> addProcuctToCategory(@PathVariable("categoryId")int categoryId,@PathVariable("productId")int productId){
        return productService.addProductToProductCategory(categoryId,productId);
    }

    @PostMapping("/api/v1/admin/products")
    public ResponseEntity<Product> addProduct(@RequestParam("name")String name,
                                              @RequestParam("price")String price,
                                              @RequestParam("quantity")String quantity,
                                              @RequestParam("minQuantity")String minQuantity,
                                              @RequestParam("description")String description,
                                              @RequestParam("generate")boolean generate,
                                              @RequestParam("barcode")String barcode,
                                              @RequestParam("categoryId")int categoryId,
                                              @RequestParam("mainImgName") String  mainImageName,
                                              @RequestParam("files")MultipartFile[] files) throws NoSuchAlgorithmException {
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
            product.setBarCodeSerial(generatedBarcodeSerial);
        }
        else {
            Optional<Product>productExist =  productRepository.findByBarCodeSerial(barcode);
            if(productExist.isPresent()){
                return new ResponseEntity(productExist.get(),HttpStatus.CONFLICT);
            }
            product.setBarCodeSerial(barcode);
        }
        if(files.length>0) {
            for(MultipartFile multipartFile :files){
                if(multipartFile.getName().equals(mainImageName)){
                    ProductImage mainProductImage = productService.createImage(multipartFile);
                    mainProductImage.setMain(true);
                    productImageList.add(mainProductImage);
                }
                else{
                    ProductImage productImage = productService.createImage(multipartFile);
                    productImageList.add(productImage);
                }
            }
        }
        if(productImageList.size()>0){
            product.setProductImages(productImageList);
        }
        category.get().addProduct(product);
        productRepository.save(product);
        productCategoryRepository.save(category.get());
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PutMapping("/api/v1/admin/products/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable("id")int productId,
                                              @RequestParam("name")String name,
                                              @RequestParam("price")String price,
                                              @RequestParam("quantity")String quantity,
                                              @RequestParam("minQuantity")String minQuantity,
                                              @RequestParam("description")String description,
                                              @RequestParam("generate")boolean generate,
                                              @RequestParam("barcode")String barcode,
                                              @RequestParam("categoryId")int categoryId,
                                              @RequestParam("mainImgName") String  mainImageName,
                                              @RequestParam("files")MultipartFile[] files) throws NoSuchAlgorithmException {

        Optional<ProductCategory> category = productCategoryRepository.findById(categoryId);
        if(category.isEmpty()){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        Optional<Product>productExist =  productService.findById(productId);
        Path productStorageLocation = Paths.get("src/main/resources/static/"+productStorageProperty.getUploadDirectory()).toAbsolutePath().normalize();
        if(productExist.isEmpty()){return new ResponseEntity(null,HttpStatus.NOT_FOUND);}
        if(productExist.get().getProductImages().size()>0) {
            for (ProductImage productImage : productExist.get().getProductImages()) {
                File file = new File(productStorageLocation.resolve(productImage.getName()).toUri());
                if (file.exists() && file.isFile()) {
                    boolean f = file.delete();
                }
            }
        }        Product product =  productExist.get();
        product.setName(name);
        product.setPrice(Double.parseDouble(price));
        product.setQuantity(Integer.parseInt(quantity));
        product.setMinQuantity(Integer.parseInt(minQuantity));
        product.setDescription(description);
        Set<ProductImage> productImageList = new HashSet<>();
        if(generate){
            String generatedBarcodeSerial = SystemUtils.generateRandomNumbers(12);
            product.setBarCodeSerial(generatedBarcodeSerial);
        }
        else {
            product.setBarCodeSerial(barcode);
        }
        if(files.length>0) {
            for(MultipartFile multipartFile :files){
                if(multipartFile.getName().equals(mainImageName)){
                    ProductImage mainProductImage = productService.createImage(multipartFile);
                    mainProductImage.setMain(true);
                    productImageList.add(mainProductImage);
                }
                else{
                    ProductImage productImage = productService.createImage(multipartFile);
                    productImageList.add(productImage);
                }
            }
        }
        if(productImageList.size()>0){
            product.setProductImages(productImageList);
        }
        category.get().addProduct(product);
        productRepository.save(product);
        productCategoryRepository.save(category.get());
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/admin/products/{id}")
    public ResponseEntity<?>deleteProduct(@PathVariable("id")int productId){
        productService.deleteProduct(productId);
        return new ResponseEntity(HttpStatus.OK);
    }

//    @DeleteMapping("/api/v1/admin/categories/{id}")
//    public ResponseEntity<?>deleteProductCategory(@PathVariable("id")int categoryId){
//        productService.deleteProductCategory(categoryId);
//        return new ResponseEntity(HttpStatus.OK);
//    }
}
