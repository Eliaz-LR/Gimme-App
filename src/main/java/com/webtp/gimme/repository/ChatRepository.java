package com.webtp.gimme.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webtp.gimme.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

}
