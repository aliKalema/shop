package com.phenomenal.shop.repository;

import com.phenomenal.shop.entity.UserSetting;
import org.springframework.data.repository.CrudRepository;

public interface UserSettingRepository extends CrudRepository<UserSetting,Integer> {
}
