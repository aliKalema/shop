package com.phenomenal.shop.service;

import com.phenomenal.shop.entity.Customer;
import com.phenomenal.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getCustomers(){
        List<Customer>customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers :: add);
        return customers;
    }
}
