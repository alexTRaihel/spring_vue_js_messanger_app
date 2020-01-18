package com.fitness.app.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.fitness.app.dto.MessagePageDto;
import com.fitness.app.models.Message;
import com.fitness.app.models.User;
import com.fitness.app.models.Views;
import com.fitness.app.services.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("messages")
public class MessagesController {

    public static final int MESSAGES_PER_PAGES = 3;

    private final MessageService messageService;

    @Autowired
    public MessagesController(MessageService messageService) {
        this.messageService = messageService;
    }

    @JsonView(Views.FullInfo.class)
    @GetMapping
    public MessagePageDto getAllMessages(
            @PageableDefault(size = MESSAGES_PER_PAGES, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){
        return messageService.findAll(pageable);
    }

    @JsonView(Views.FullInfo.class)
    @GetMapping("{id}")
    public Message getMessage(@PathVariable("id") Message message){
        return message;
    }

    @PostMapping
    public Message createMessage(
            @RequestBody Message message,
            @AuthenticationPrincipal User user
    ) throws IOException{
        message.setLocalDateTime(LocalDateTime.now());
        message.setAuthor(user);
        return messageService.save(message);
    }

    @PutMapping("{id}")
    public Message updateMessage(
            @PathVariable("id") Message messageFromDb,
            @RequestBody Message message) throws IOException {
        return messageService.update(message, messageFromDb);
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Message message){
        messageService.delete(message);
    }
}
