package com.webtp.gimme.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.webtp.gimme.model.Chat;
import com.webtp.gimme.service.ChatService;
import com.webtp.gimme.utils.SecurityUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/customers/{username}")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/chats")
    public List<Chat> getAllChats(@PathVariable String username) {
        SecurityUtils.checkAuthorization(username);
        return chatService.getChats(username);
    }

    @GetMapping("/chats/{id}")
    public Chat getChat(@PathVariable String username, @PathVariable UUID id) {
        SecurityUtils.checkAuthorization(username);
        return chatService.getChat(username, id);
    }

    @PostMapping("/chats")
    public ResponseEntity<?> createChat(@PathVariable String username, @RequestBody String recipient) {
        SecurityUtils.checkAuthorization(username);
        Chat chat = new Chat();
        chat.setUsername(username);
        chat.setUsernameOfRecipient(recipient);
        chatService.createChat(chat);
        return ResponseEntity.ok(chat);
    }

    @PutMapping("/chats/{id}")
    public ResponseEntity<?> sendMessage(@PathVariable String username, @PathVariable UUID id, @RequestBody String msg) { 
        SecurityUtils.checkAuthorization(username);
        Chat chat = chatService.getChat(username, id);
        if (chat == null) {
            return ResponseEntity.notFound().build();
        }
        chatService.sendMessage(chat, msg);
        return ResponseEntity.ok(chat);
    }
    
}
