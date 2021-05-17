package com.example.apicheckpointkiwi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tweets")
public class JTweetController {

    JTweetService jTweetService;

    public JTweetController(JTweetService jTweetService) {
        this.jTweetService = jTweetService;
    }

    @GetMapping
    public ResponseEntity<JTweets> getTweets() {
        JTweets jTweets = jTweetService.getTweets();

        return jTweets.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(jTweets);
    }
}
