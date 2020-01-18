package com.fitness.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fitness.app.dto.MessagePageDto;
import com.fitness.app.models.User;
import com.fitness.app.models.Views;
import com.fitness.app.repo.UserDetailsRepo;
import com.fitness.app.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private final UserDetailsRepo userDetailsRepo;

    private MessageService messageService;
    private ObjectWriter messageWriter;
    private ObjectWriter profileWriter;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(UserDetailsRepo userDetailsRepo, MessageService messageService, ObjectMapper objectMapper){
        this.userDetailsRepo = userDetailsRepo;
        this.messageService = messageService;

        this.messageWriter = objectMapper
                .setConfig(objectMapper.getSerializationConfig())
                .writerWithView(Views.FullInfo.class);

        this.profileWriter = objectMapper
                .setConfig(objectMapper.getSerializationConfig())
                .writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {

        HashMap<Object, Object> data = new HashMap<>();

        if (isAuthorized(user)){

            User userFromDb = userDetailsRepo.findById(user.getId()).get();
            model.addAttribute("profile", profileWriter.writeValueAsString(userFromDb));
            getPageRequest();

            MessagePageDto messagePageDto = this.messageService.findAll(getPageRequest());

            model.addAttribute("messages", messageWriter.writeValueAsString(messagePageDto.getMessages()));
            data.put("currentPage", messagePageDto.getCurrentPage());
            data.put("totalPages", messagePageDto.getTotalPages());

        } else {
            model.addAttribute("messages", "[]");
            model.addAttribute("profile", "null");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }

    private PageRequest getPageRequest() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return PageRequest.of(0, MessagesController.MESSAGES_PER_PAGES, sort);
    }

    private boolean isAuthorized(User user){
        return user != null;
    }
}
