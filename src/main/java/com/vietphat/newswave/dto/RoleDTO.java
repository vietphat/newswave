package com.vietphat.newswave.dto;

import com.vietphat.newswave.enums.UserRole;

import java.util.Date;

public class RoleDTO extends BaseDTO<RoleDTO> {

    private String name;

    private UserRole code;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String name, UserRole code) {
        super(id, createdBy, createdDate, modifiedBy, modifiedDate);
        this.name = name;
        this.code = code;
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

    public boolean compareTo(UserRole userRole) {

        return this.code.equals(userRole);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
