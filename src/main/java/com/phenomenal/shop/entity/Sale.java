package com.phenomenal.shop.entity;

import com.phenomenal.shop.entity.SoldItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime dateTime;
    @OneToOne(cascade= CascadeType.MERGE,fetch= FetchType.EAGER)
    private Customer customer;
    @OneToOne(cascade= CascadeType.MERGE,fetch= FetchType.EAGER)
    private User user;
    @OneToMany(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinColumn(name="sale_id")
    private Set<SoldItem> soldItems;
}
