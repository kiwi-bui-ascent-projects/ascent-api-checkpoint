package com.example.apicheckpointkiwi;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JTweetService {

    JTweetsRepository jTweetsRepository;

    public JTweetService(JTweetsRepository jTweetsRepository) {
        this.jTweetsRepository = jTweetsRepository;
    }

    public JTweets getTweets() {
        List<JTweet> tweets = jTweetsRepository.findAll();

        return new JTweets(tweets);
    }

    public JTweets getTweets(String author, String date) {
        return null;
    }

    public JTweet addTweet(JTweet jTweet) {
        return null;
    }

    public JTweet getTweet(long id) {
        return null;
    }

    public JTweet updateTweet(long id, JTweetUpdate jTweetUpdate) {
        return null;
    }

    public void deleteTweet(long id) {
    }
}
