package com.fitness.app.services;

import com.fitness.app.models.User;

public interface ProfileService {

   User changeSubscription(User channel, User subscriber);
}
