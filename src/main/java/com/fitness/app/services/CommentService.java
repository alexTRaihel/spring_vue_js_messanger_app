package com.fitness.app.services;

import com.fitness.app.models.Comment;
import com.fitness.app.models.User;

public interface CommentService {

    Comment create(Comment comment, User author);
}
