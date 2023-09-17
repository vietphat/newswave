package com.vietphat.newswave.repository;

import com.vietphat.newswave.entity.SavedPostEntity;
import com.vietphat.newswave.entity.key.SavedPostId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedPostRepository extends JpaRepository<SavedPostEntity, SavedPostId> {

}
