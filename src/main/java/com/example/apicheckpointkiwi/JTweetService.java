package com.example.apicheckpointkiwi;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<JTweet> getTweet(long id) {
        return jTweetsRepository.findById(id);
    }

    public JTweet updateTweet(long id, JTweetUpdate jTweetUpdate) {
        Optional<JTweet> tweet = jTweetsRepository.findById(id);

        if (tweet.isPresent()) {
            return jTweetsRepository.save(tweet.get());
        }

        return null;
    }

    public void deleteTweet(long id) {
    }
}
