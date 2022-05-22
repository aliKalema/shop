package com.phenomenal.shop.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique =true,length = 50)
    private String username;
    private String password;
    private String email;
    private String phone;
    @Column(columnDefinition = "boolean default true")
    private boolean enabled;
    @OneToMany(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinColumn(name="user_id")
    private List<Role> roles;
    @OneToOne(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinColumn(name="user_setting_id")
    private UserSetting userSetting;
    @OneToOne(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinColumn(name="user_image_id")
    private UserImage userImage;
    @Transient
    private boolean online;

}
