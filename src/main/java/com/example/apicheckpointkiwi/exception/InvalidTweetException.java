package com.example.apicheckpointkiwi.exception;

public class InvalidTweetException extends RuntimeException {
    public InvalidTweetException(String message) {
        super(message);
    }
}
