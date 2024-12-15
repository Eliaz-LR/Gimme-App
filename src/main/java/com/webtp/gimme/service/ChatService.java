package com.webtp.gimme.service;


import java.sql.Timestamp;
import com.webtp.gimme.model.Chat;
import com.webtp.gimme.model.Messages;
import com.webtp.gimme.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public List<Chat> getChats(String username) {
        List<Chat> chats = chatRepository.findAll();
        for (Chat chat : chats) {
            if (!chat.getUsername().contains(username)) {
                chats.remove(chat);
            } else {
                chat.getUsername().remove(username);
            }
        }
        return chats;
    }

    public Chat getChat(String username, UUID id) {
        Chat chat = chatRepository.findById(id).orElse(null);
        if (chat != null && chat.getUsername().contains(username)) {
            return chat;
        } else {
            return null;
        }
    }

    public void createChat(Chat chat) {
        chatRepository.save(chat);
    }

    public Chat sendMessage(String username, UUID id, String msg) {
        Messages message = new Messages();
        message.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        message.setMessage(msg);
        message.setSender(username);

        Chat chat = chatRepository.findById(id).orElse(null);

        chat.addMessage(message);
        
        return chatRepository.save(chat);
    }
}
