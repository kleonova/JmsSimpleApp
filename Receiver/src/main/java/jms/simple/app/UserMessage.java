package jms.simple.app;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserMessage {
    private String message;
    private String username;
    private LocalDateTime timestamp;
}
