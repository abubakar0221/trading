package com.example.demo.controller;

import com.example.demo.model.ChatMessage;
import com.example.demo.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatService;

    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessage message) {
        return ResponseEntity.ok(chatService.sendMessage(message));
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<ChatMessage>> getConversation(
            @RequestParam String userId,
            @RequestParam String adminId) {
        return ResponseEntity.ok(chatService.getConversation(userId, adminId));
    }

}
