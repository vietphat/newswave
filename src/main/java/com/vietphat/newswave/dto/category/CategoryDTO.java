package com.vietphat.newswave.dto.category;

import com.vietphat.newswave.dto.BaseDTO;
import com.vietphat.newswave.service.CategoryService;
import com.vietphat.newswave.validation.uniquefield.UniqueField;
import jakarta.validation.constraints.NotNull;

public class CategoryDTO extends BaseDTO {

    @NotNull(message = "Tên danh mục là bắt buộc")
    private String name;

    @NotNull(message = "Mã danh mục là bắt buộc")
    @UniqueField(fieldName = "code", message = "Mã danh mục đã tồn tại", service = CategoryService.class)
    private String code;

    public CategoryDTO() {
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
