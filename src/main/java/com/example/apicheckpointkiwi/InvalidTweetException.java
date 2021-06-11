package com.example.apicheckpointkiwi;

public class InvalidTweetException extends RuntimeException {
    public InvalidTweetException(String message) {
        super(message);
    }
}
