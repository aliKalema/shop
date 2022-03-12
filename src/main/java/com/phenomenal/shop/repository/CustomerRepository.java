package com.phenomenal.shop.repository;

import com.phenomenal.shop.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Integer> {
}
