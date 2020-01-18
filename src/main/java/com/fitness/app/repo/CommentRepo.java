package com.fitness.app.repo;

import com.fitness.app.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {

}
