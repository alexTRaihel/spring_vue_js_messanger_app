package com.fitness.app.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@ToString( of = {"id", "name"})
@EqualsAndHashCode(of = "id")
@Data
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdName.class)
    private Long id;
    @JsonView(Views.IdName.class)
    private String name;
    private String type;
    @JsonView(Views.IdName.class)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullInfo.class)
    private User author;

    @OneToMany(mappedBy = "message", orphanRemoval = true)
    @JsonView(Views.FullInfo.class)
    private List<Comment> comments;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    @JsonView(Views.IdName.class)
    private String link;
    @JsonView(Views.IdName.class)
    private String linkTitle;
    @JsonView(Views.IdName.class)
    private String linkDescription;
    @JsonView(Views.IdName.class)
    private String linkCover;
}
