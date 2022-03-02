package com.phenomenal.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable=false, updatable=false)
    private int id;
    private String name;
    @Column(name="mime_type")
    private String mimeType;
    private long size =0;
    @Column(columnDefinition = "boolean default false")
    private boolean main;
}
