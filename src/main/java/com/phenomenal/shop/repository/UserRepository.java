package com.phenomenal.shop.repository;

import com.phenomenal.shop.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
