package com.vietphat.newswave.dto.tag;

import com.vietphat.newswave.dto.BaseDTO;
import com.vietphat.newswave.service.TagService;
import com.vietphat.newswave.validation.uniquefield.UniqueField;
import jakarta.validation.constraints.NotNull;

public class TagDTO extends BaseDTO<TagDTO> {

    @NotNull(message = "Tên thẻ là bắt buộc")
    private String name;

    @NotNull(message = "Mã thẻ là bắt buộc")
    @UniqueField(fieldName = "code", message = "Mã thẻ đã tồn tại", service = TagService.class)
    private String code;

    public TagDTO() {
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
