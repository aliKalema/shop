package com.phenomenal.shop.repository;

import com.phenomenal.shop.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Integer> {
    Optional<Product> findByBarCodeSerial(String barcode);
}
