package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.contact.ContactDTO;
import com.vietphat.newswave.dto.contact.ResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {

    List<ContactDTO> findAll();

    ContactDTO findById(Long id);

    ContactDTO searchContactsWithPagination(Pageable pageable, String search);

    void delete(String ids[]);

    ContactDTO response(ResponseDTO responseDTO);

}
