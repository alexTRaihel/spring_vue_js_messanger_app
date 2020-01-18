package com.fitness.app.controllers;

        import com.fasterxml.jackson.annotation.JsonView;
        import com.fitness.app.models.Comment;
        import com.fitness.app.models.User;
        import com.fitness.app.models.Views;
        import com.fitness.app.services.CommentService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.core.annotation.AuthenticationPrincipal;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @JsonView(Views.FullComment.class)
    public Comment create(
            @RequestBody Comment comment,
            @AuthenticationPrincipal User user
    ){
        return commentService.create(comment, user);
    }
}
