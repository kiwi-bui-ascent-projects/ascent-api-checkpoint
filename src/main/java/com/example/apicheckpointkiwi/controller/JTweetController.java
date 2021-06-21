package com.example.apicheckpointkiwi.controller;

import com.example.apicheckpointkiwi.service.JTweetService;
import com.example.apicheckpointkiwi.model.JTweetUpdate;
import com.example.apicheckpointkiwi.model.JTweets;
import com.example.apicheckpointkiwi.exception.InvalidTweetException;
import com.example.apicheckpointkiwi.model.JTweet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweets")
public class JTweetController {
    JTweetService jTweetService;

    public JTweetController(JTweetService jTweetService) {
        this.jTweetService = jTweetService;
    }

    @GetMapping
    public ResponseEntity<JTweets> getTweets(@RequestParam (required = false) String author,
                                             @RequestParam (required = false) String date) {
        JTweets jTweets;

        if (author == null && date == null) {
            jTweets = jTweetService.getTweets();
        } else {
            jTweets = jTweetService.getTweets(author, date);
        }

        return jTweets.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(jTweets);
    }

    @PostMapping
    public ResponseEntity<JTweet> postTweet(@RequestBody JTweet jTweet) {
        return ResponseEntity.ok(jTweetService.addTweet(jTweet));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JTweet> getTweet(@PathVariable long id) {
        JTweet jTweet = jTweetService.getTweet(id);

        return jTweet == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(jTweet);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JTweet> patchTweet(@PathVariable long id,
                                             @RequestBody JTweetUpdate jTweetUpdate) {
        return ResponseEntity.ok(jTweetService.updateTweet(id, jTweetUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable long id) {
        jTweetService.deleteTweet(id);

        return ResponseEntity.accepted().build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidTweetExceptionHandler(InvalidTweetException e) {}
}
