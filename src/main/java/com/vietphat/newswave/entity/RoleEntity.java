package com.vietphat.newswave.entity;

import com.vietphat.newswave.enums.UserRole;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true)
    private UserRole code;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users = new ArrayList<>();

    public RoleEntity() {
    }

    public RoleEntity(String name, UserRole role) {
        this.name = name;
        this.code = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getCode() {
        return code;
    }

    public void setCode(UserRole code) {
        this.code = code;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
