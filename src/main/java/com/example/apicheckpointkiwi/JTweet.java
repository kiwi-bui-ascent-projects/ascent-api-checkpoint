package com.example.apicheckpointkiwi;

import java.time.LocalDate;

public class JTweet {
    private long id;
    private String author;
    private String body;
    private LocalDate localDate;

    public JTweet() {

    }

    public JTweet(long id, String author, String body) {
        this.id = id;
        this.author = author;
        this.body = body;
        this.localDate = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}

