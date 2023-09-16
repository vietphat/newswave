package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.EmailDetails;
import com.vietphat.newswave.dto.contact.ContactDTO;
import com.vietphat.newswave.dto.contact.ResponseDTO;
import com.vietphat.newswave.entity.ContactEntity;
import com.vietphat.newswave.repository.ContactRepository;
import com.vietphat.newswave.service.ContactService;
import com.vietphat.newswave.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private ModelMapper modelMapper;

    private ContactRepository contactRepository;

    private EmailService emailService;

    @Autowired
    public ContactServiceImpl(ModelMapper modelMapper, ContactRepository contactRepository, EmailService emailService) {
        this.modelMapper = modelMapper;
        this.contactRepository = contactRepository;
        this.emailService = emailService;
    }

    @Override
    public List<ContactDTO> findAll() {

        List<ContactEntity> contacts = contactRepository.findAll();

        List<ContactDTO> contactDTOs = contacts.stream()
                .map(contact -> modelMapper.map(contact, ContactDTO.class))
                .collect(Collectors.toList());

        return contactDTOs;
    }

    @Override
    public ContactDTO findById(Long id) {

        ContactEntity contact = contactRepository.findById(id).orElse(null);

        return contact == null ? null : modelMapper.map(contact, ContactDTO.class);
    }

    @Override
    public ContactDTO searchContactsWithPagination(Pageable pageable, String search) {

        ContactDTO contactDTO = new ContactDTO();

        Page<ContactEntity> page = contactRepository.searchContactsWithPagination(pageable, search);

        if (search != null) {
            contactDTO.setSearch(search);
        }

        contactDTO.setCurrentPage(page.getNumber() + 1);
        contactDTO.setTotalPages(page.getTotalPages());
        contactDTO.setListResult(
                page.getContent().stream()
                        .map(contact -> modelMapper.map(contact, ContactDTO.class))
                        .collect(Collectors.toList())
        );

        return contactDTO;
    }

    @Override
    @Transactional
    public void delete(String[] ids) {
        try {
            for (String id : ids) {
                ContactEntity contact = contactRepository.findById(Long.parseLong(id)).orElse(null);

                if (contact != null) {

                    contactRepository.delete(contact);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    @Transactional
    public ContactDTO response(ResponseDTO responseDTO) {

        ContactEntity contact = contactRepository.findById(responseDTO.getContactId()).orElse(null);

        ContactDTO respondedContactDTO = null;
        if (emailService.sendSimpleMail(new EmailDetails(contact.getEmail(), responseDTO.getContent(), responseDTO.getTitle()))) {
            contact.setResponded(true);
            ContactEntity respondedContact = contactRepository.save(contact);
            respondedContactDTO = modelMapper.map(respondedContact, ContactDTO.class);
        }

        return respondedContactDTO;
    }
}
