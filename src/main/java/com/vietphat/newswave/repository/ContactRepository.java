package com.vietphat.newswave.repository;

import com.vietphat.newswave.entity.ContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    @Query("SELECT c FROM ContactEntity c " +
            "WHERE :search IS NULL OR LOWER(c.fullName) LIKE %:search% " +
            "OR LOWER(c.email) LIKE %:search% OR LOWER(c.title) LIKE %:search%")
    Page<ContactEntity> searchContactsWithPagination(Pageable pageable, @Param("search") String search);

}
