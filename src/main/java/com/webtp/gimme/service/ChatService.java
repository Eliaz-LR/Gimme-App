package com.webtp.gimme.service;


import java.sql.Timestamp;
import com.webtp.gimme.model.Chat;
import com.webtp.gimme.model.Messages;
import com.webtp.gimme.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public List<Chat> getChats(String username) {
        return chatRepository.findByUsername(username);
    }

    public Chat getChat(String username, UUID id) {
        Chat chat = chatRepository.findById(id).orElse(null);
        if (chat != null && chat.getUsername().equals(username)) {
            return chat;
        } else {
            return null;
        }
    }

    public void createChat(Chat chat) {
        chatRepository.save(chat);
    }

    public void sendMessage(Chat chat, String msg) {
        Messages message = new Messages();
        message.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        message.setMessage(msg);
        message.setSender(chat.getUsername());

        chat.addMessage(message);
        chatRepository.save(chat);
    }
}
