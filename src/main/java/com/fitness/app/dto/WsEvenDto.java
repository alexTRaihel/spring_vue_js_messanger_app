package com.fitness.app.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.fitness.app.models.Views;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonView(Views.IdName.class)
public class WsEvenDto {

    private ObjectType objectType;
    private EventType eventType;
    @JsonRawValue
    private String body;
}
