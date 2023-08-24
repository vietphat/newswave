package com.vietphat.newswave.dto;

import java.util.Date;

public class RoleDTO extends BaseDTO {

    private String name;

    private String code;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String name, String code) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
