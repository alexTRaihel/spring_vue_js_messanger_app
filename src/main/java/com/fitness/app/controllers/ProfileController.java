package com.fitness.app.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.fitness.app.models.User;
import com.fitness.app.models.Views;
import com.fitness.app.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullInfo.class)
    public User get( @PathVariable("id") User user) {
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullInfo.class)
    public User changeSubscription(
            @AuthenticationPrincipal User subscriber,
            @PathVariable("channelId") User channel
    ){
        if (channel.equals(subscriber)){
            return channel;
        } else {
            return profileService.changeSubscription(channel, subscriber);
        }
    }
}
