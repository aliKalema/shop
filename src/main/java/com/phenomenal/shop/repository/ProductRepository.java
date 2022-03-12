package com.phenomenal.shop.repository;

import com.phenomenal.shop.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Integer> {
}
