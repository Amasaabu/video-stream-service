package com.video.stream.errors;

public class BadRequest extends RuntimeException {
    public BadRequest(String message) {
        super(message);
    }
}
