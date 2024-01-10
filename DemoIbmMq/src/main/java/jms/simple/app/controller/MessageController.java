package jms.simple.app.controller;

import jms.simple.app.model.MessageModel;
import jms.simple.app.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("messages")
@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;


    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody MessageModel message) {
        messageService.sendMessage(message);
        return new ResponseEntity(message, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<MessageModel> findMessage(@RequestParam String correlationId) {
        String foundMessageText = messageService.findMessage(correlationId);

        MessageModel responseMessage = MessageModel.builder()
                .message(foundMessageText)
                .identifier(correlationId)
                .build();

        return new ResponseEntity(responseMessage, HttpStatus.OK);
    }
}
