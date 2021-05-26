package com.example.apicheckpointkiwi;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "j_tweets")
public class JTweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String author;
    private String body;
    @Column(name = "timestamp")
    private String localDate;

    public JTweet() {
        this.localDate = LocalDate.now().toString();
    }

    public JTweet(String author, String body) {
        this.author = author;
        this.body = body;
        this.localDate = LocalDate.now().toString();
    }

    public JTweet(long id, String author, String body) {
        this.id = id;
        this.author = author;
        this.body = body;
        this.localDate = LocalDate.now().toString();
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

    public String getLocalDate() {
        return this.localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        String output = "{\n" +
                "  \"id\": \"" + this.id + "\"\n" +
                "  \"author\": \"" + this.author + "\"\n" +
                "  \"body\": \"" + this.body + "\"\n" +
                "  \"timestamp\": \"" + this.localDate + "\"\n" +
                "}";

        return output;
    }
}

