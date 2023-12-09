package com.pbl4.monolingo.rest;


import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.Message;
import com.pbl4.monolingo.service.ChatService;
import com.pbl4.monolingo.utility.dto.MessageDTO;
import com.pbl4.monolingo.utility.dto.OnlineStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatRestController {
    private final ChatService chatService;
    private final AccountRepository accountRepository;

    @Autowired
    public ChatRestController(ChatService chatService, AccountRepository accountRepository) {
        this.chatService = chatService;
        this.accountRepository = accountRepository;
    }

    @MessageMapping("/chat.sendMessage/{receiverUserId}")
    @SendTo("/topic/messages/{receiverUserId}")
    public MessageDTO sendMessage(@Payload MessageDTO messageDTO,
                            Principal principal,@DestinationVariable String receiverUserId) {
        Account accountCurrent = accountRepository.findByUsername(principal.getName()).get();
        chatService.sendMessage(accountCurrent.getAccountId(), messageDTO.getReceiverUserId(), messageDTO.getContent());
        messageDTO.setTimestamp(LocalDateTime.now());
        return messageDTO;
    }

    @MessageMapping("/setOnlineStatus")
    public void setOnlineStatus(@Payload OnlineStatusDTO onlineStatusDTO, Principal principal) {
        chatService.setOnlineStatus(principal.getName(), onlineStatusDTO.isOnline());
    }
    @GetMapping("/messages/{sender}/{receiver}")
    public Page<Message> getMessages(
            @PathVariable Integer sender,
            @PathVariable Integer receiver,
            @PageableDefault(size = 200, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        return chatService.getMessagePaging(sender, receiver, pageable);
    }
    @GetMapping("/messageList/{sender}/{receiver}")
    public List<Message> getAllMessage(@PathVariable Integer sender,
                                       @PathVariable Integer receiver) {
        return chatService.showAllMessageAccountWithFriend(sender,receiver);
    }


    // Other HTTP endpoints and methods for handling user interface
}
