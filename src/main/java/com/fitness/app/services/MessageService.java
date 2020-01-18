package com.fitness.app.services;

import com.fitness.app.dto.MessagePageDto;
import com.fitness.app.models.Message;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface MessageService {

    MessagePageDto findAll(Pageable pageable);
    Message save(Message message) throws IOException;
    Message update(Message message, Message messageFromDB) throws IOException;
    void delete(Message message);
}
