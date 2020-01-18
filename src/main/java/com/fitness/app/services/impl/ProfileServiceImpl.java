package com.fitness.app.services.impl;

import com.fitness.app.models.User;
import com.fitness.app.repo.UserDetailsRepo;
import com.fitness.app.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserDetailsRepo userDetailsRepo;

    @Autowired
    public ProfileServiceImpl(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }

    @Override
    public User changeSubscription(User channel, User subscriber) {

        Set<User> subscribers = channel.getSubscribers();

        if (subscribers.contains(subscriber)){
            subscribers.remove(subscriber);
        } else {
            subscribers.add(subscriber);
        }

        return userDetailsRepo.save(channel);
    }
}
