package com.pbl4.monolingo.service;


import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.dao.MessageRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.Message;
import com.pbl4.monolingo.utility.ListToPageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChatService {
    private final SimpMessagingTemplate messagingTemplate;
    private final AccountRepository userRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public ChatService(SimpMessagingTemplate messagingTemplate, AccountRepository userRepository, MessageRepository messageRepository) {
        this.messagingTemplate = messagingTemplate;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public void sendMessage(Integer senderUserId, Integer receiverUserId, String content) {
        // Save the message to the database
        Account sender = userRepository.findById(senderUserId).orElseThrow();
        Account receiver = userRepository.findById(receiverUserId).orElseThrow();
        Message message = new Message(content, sender, receiver, LocalDateTime.now());
        messageRepository.save(message);

        // Send the message to the recipient
//        messagingTemplate.convertAndSendToUser(receiverUsername, "/topic/messages", message);
    }

    public void setOnlineStatus(String username, boolean online) {
        Account user = userRepository.findByUsername(username).orElseThrow();
        user.setOnline(online);
        userRepository.save(user);
    }
    public List<Message>showAllMessageAccountWithFriend(Integer accountId, Integer fiendId){
        List<Message> rs = new ArrayList<Message>();
        List<Message> accountMessage = messageRepository.findBySenderAccountIdAndReceiverAccountId(accountId,fiendId);
        List<Message> friendMessage = messageRepository.findBySenderAccountIdAndReceiverAccountId(fiendId,accountId);
        rs.addAll(accountMessage);
        rs.addAll(friendMessage);
        Collections.sort(rs);
        return rs;
    }
    public Page<Message> getMessagePaging(Integer accountId, Integer friendId, Pageable pageable){
        List<Message> rs = new ArrayList<Message>();
        List<Message> accountMessage = messageRepository.findBySenderAccountIdAndReceiverAccountId(accountId,friendId);
        List<Message> friendMessage = messageRepository.findBySenderAccountIdAndReceiverAccountId(friendId,accountId);
        rs.addAll(accountMessage);
        rs.addAll(friendMessage);
        return ListToPageConverter.convertListToPage(rs,pageable);
    }
}