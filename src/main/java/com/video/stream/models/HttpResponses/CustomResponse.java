package com.video.stream.models.HttpResponses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CustomResponse {
    private Object message;
    private String code;
    private String responseTime= String.valueOf(LocalDateTime.now());
}
