package com.prashant.SmartContactManager.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    private String content;

    @Builder.Default
    private MessageType type = MessageType.blue;
}
