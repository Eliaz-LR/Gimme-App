package com.webtp.gimme.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webtp.gimme.model.Search;

@Repository
public interface SearchRepository extends JpaRepository<Search, UUID> {
    
}
