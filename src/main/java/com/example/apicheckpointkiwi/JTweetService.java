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
        List<JTweet> tweets = jTweetsRepository.findByAuthorAndLocalDate(author, date);

        return new JTweets(tweets);
    }

    public JTweet addTweet(JTweet jTweet) {
        if (jTweet.getId() < 0 || jTweet.getAuthor() == "" || jTweet.getBody() == "") {
            throw new InvalidTweetException("Invalid Tweet");
        }
        return jTweetsRepository.save(jTweet);
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
