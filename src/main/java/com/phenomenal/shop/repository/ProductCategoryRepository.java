package com.phenomenal.shop.repository;

import com.phenomenal.shop.entity.ProductCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory,Integer> {
    @Query(value="SELECT * FROM product_category INNER JOIN product ON product_category.id =  product.product_category_id AND  product.id = ?",nativeQuery= true)
    Optional<ProductCategory> findCategoryByProductId(int productId);
}
