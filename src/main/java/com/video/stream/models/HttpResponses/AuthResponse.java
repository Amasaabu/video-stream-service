package com.video.stream.models.HttpResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    @Getter
    @Setter
    public static class message {
        private int id;
        private String firstname;
        private String lastname;
        private String email;
        private String subsciptionId;
    }
    private message message;
}
