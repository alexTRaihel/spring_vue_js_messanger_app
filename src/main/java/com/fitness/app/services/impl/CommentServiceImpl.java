package com.fitness.app.services.impl;

import com.fitness.app.dto.EventType;
import com.fitness.app.dto.ObjectType;
import com.fitness.app.models.Comment;
import com.fitness.app.models.User;
import com.fitness.app.models.Views;
import com.fitness.app.repo.CommentRepo;
import com.fitness.app.services.CommentService;
import com.fitness.app.utils.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentServiceImpl(CommentRepo commentRepo, WsSender wsSender){
        this.commentRepo = commentRepo;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User author){
        comment.setAuthor(author);
        Comment commentFromDb = commentRepo.save(comment);
        wsSender.accept(EventType.CREATE, commentFromDb);
        return commentFromDb;
    }
}
