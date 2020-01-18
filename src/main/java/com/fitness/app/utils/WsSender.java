package com.fitness.app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fitness.app.dto.EventType;
import com.fitness.app.dto.ObjectType;
import com.fitness.app.dto.WsEvenDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class WsSender {

    private SimpMessagingTemplate template;
    private ObjectMapper objectMapper;

    public WsSender(SimpMessagingTemplate template, ObjectMapper objectMapper) {
        this.template = template;
        this.objectMapper = objectMapper;
    }

    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType, Class view) {

        ObjectWriter objectWriter = objectMapper
                .setConfig(objectMapper.getSerializationConfig())
                .writerWithView(view);

        return (EventType eventType, T payload)->{

            String value = null;

            try {
                value = objectWriter.writeValueAsString(payload);
            } catch (JsonProcessingException e) {
               throw new RuntimeException(e);
            }

            template.convertAndSend("/topic/activity", new WsEvenDto(objectType, eventType, value));
        };
    }
}
