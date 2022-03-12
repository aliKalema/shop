package com.phenomenal.shop.repository;

import com.phenomenal.shop.entity.SoldItem;
import org.springframework.data.repository.CrudRepository;

public interface SoldItemRepository extends CrudRepository<SoldItem,Integer> {
}
