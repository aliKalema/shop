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
        products.forEach(this:: setProductStructure);
        return products;
    }

    public List<ProductCategory> getAllProductCategories(){
        List<ProductCategory>categories = new ArrayList<>();
        productCategoryRepository.findAll().forEach(categories::add);
        categories.forEach(this::setProductCategoryStructure);
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

    public void setProductCategoryStructure(ProductCategory productCategory){
        double total= 0;
        int quantity = 0;
        for(Product product :productCategory.getProducts()){
            total =total +  (product.getQuantity() * product.getPrice());
            quantity = quantity + product.getQuantity();
        }
        productCategory.setQuantity(quantity);
        productCategory.setTotal(total);
    }

    public void setProductStructure(Product product){
        String status = "";
        ProductImage mainImage = null;
        List<ProductImage>additionalImages = new ArrayList<>();
        if(product.getQuantity()>product.getMinQuantity()){
            status = "IN STOCK";
        }
        else if(product.getQuantity()<=product.getMinQuantity() & product.getQuantity()>0){
            status = "BELOW MINIMUM";
        }
        else{
            status = "OUT OF STOCK";
        }
        product.setStatus(status);
        if(product.getProductImages().size()>0){
            for(ProductImage productImage : product.getProductImages()){
               if(productImage.isMain()){
                   mainImage = productImage;
                }
               else{
                   additionalImages.add(productImage);
               }
            }
            if(mainImage== null && product.getProductImages().size()>0){
                mainImage = (ProductImage) new ArrayList(product.getProductImages()).get(0);
            }
        }
        else{
            mainImage = new ProductImage("noproduct.png");
        }
        product.setMainImage(mainImage);
        product.setAdditionalImages(additionalImages);
    }
}
