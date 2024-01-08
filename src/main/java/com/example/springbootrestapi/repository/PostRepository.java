package com.example.springbootrestapi.repository;

import com.example.springbootrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    // CRUD OPERATIONS ARE PROVIDED BY JPA_REPOSITORY (SUPPORTS PAGINATION AND SORTING).
}
