package com.phenomenal.shop.service;

import com.phenomenal.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarcodeService {
    @Autowired
    ProductRepository productRepository;
    public void generateBarcode(String barcode){
    }
}
