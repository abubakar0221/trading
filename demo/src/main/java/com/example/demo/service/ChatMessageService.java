package com.example.demo.service;

import com.example.demo.model.ChatMessage;
import com.example.demo.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatRepo;

    public ChatMessage sendMessage(ChatMessage message) {
        return chatRepo.save(message);
    }

    public List<ChatMessage> getConversation(String userId, String adminId) {
        return chatRepo.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
                userId, adminId, userId, adminId);
    }

}
