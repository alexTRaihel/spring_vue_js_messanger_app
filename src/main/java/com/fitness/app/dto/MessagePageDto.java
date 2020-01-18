package com.fitness.app.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fitness.app.models.Message;
import com.fitness.app.models.Views;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonView({Views.FullInfo.class})
public class MessagePageDto {
    private List<Message> messages;
    private int currentPage;
    private int totalPages;
}
