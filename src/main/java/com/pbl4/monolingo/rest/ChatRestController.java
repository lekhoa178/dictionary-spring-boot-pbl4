package com.pbl4.monolingo.rest;


import com.pbl4.monolingo.entity.Message;
import com.pbl4.monolingo.service.ChatService;
import com.pbl4.monolingo.utility.dto.MessageDTO;
import com.pbl4.monolingo.utility.dto.OnlineStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
@RestController
public class ChatRestController {
    private final ChatService chatService;

    @Autowired
    public ChatRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload MessageDTO messageDTO, Principal principal) {
        chatService.sendMessage(principal.getName(), messageDTO.getReceiver(), messageDTO.getContent());
    }

    @MessageMapping("/setOnlineStatus")
    public void setOnlineStatus(@Payload OnlineStatusDTO onlineStatusDTO, Principal principal) {
        chatService.setOnlineStatus(principal.getName(), onlineStatusDTO.isOnline());
    }
    @GetMapping("/messages")
    public Page<Message> getMessages(
            @RequestParam Integer sender,
            @RequestParam Integer receiver,
            @PageableDefault(size = 200, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        return chatService.getMessagePaging(sender, receiver, pageable);
    }

    // Other HTTP endpoints and methods for handling user interface
}
