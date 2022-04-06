package com.phenomenal.shop.service;

import com.phenomenal.shop.entity.Sale;
import com.phenomenal.shop.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesService {
    @Autowired
    SaleRepository saleRepository;

    public List<Sale> getAllSales() {
        List<Sale> sales =  new ArrayList<>();
       saleRepository.findAll().forEach(sales:: add);
       return sales;
    }
}
