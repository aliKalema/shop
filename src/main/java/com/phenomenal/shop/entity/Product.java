package com.phenomenal.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String barCodeSerial;
    private String description;
    private int minQuantity;
    @OneToMany(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinColumn(name="product_id")
    private Set<ProductImage> productImages;
    @Transient private ProductImage mainImage;
    @Transient private List<ProductImage> additionalImages;
    @Transient private String status;
    @Transient private String category;
}
