package com.phenomenal.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade= CascadeType.MERGE,fetch= FetchType.EAGER)
    @JoinColumn(name="product_category_id")
    private Set<Product>products;
    @Transient private double total;
    @Transient private int quantity;
    public void addProduct(Product product) {
        this.products.add(product);
    }
}
