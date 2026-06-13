package com.codeMentra.backend.dto.conversation;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponse {
    private String response;
    private Integer hintLevel;
    private Long conversationId;
    private LocalDateTime timeStamp;
}
