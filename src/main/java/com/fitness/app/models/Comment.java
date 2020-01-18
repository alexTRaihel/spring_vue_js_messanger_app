package com.fitness.app.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table
@EqualsAndHashCode(of = {"id"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdName.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "message_id")
    @JsonView(Views.FullComment.class)
    private Message message;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false, updatable = false)
    @JsonView(Views.IdName.class)
    private User author;

}
