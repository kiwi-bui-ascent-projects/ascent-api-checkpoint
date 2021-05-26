package com.example.apicheckpointkiwi;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        if (jTweet.getId() < 0 || jTweet.getAuthor() == "" || jTweet.getBody() == "" ||
        jTweet.getAuthor() == null || jTweet.getBody() == null) {
            throw new InvalidTweetException("Invalid Tweet");
        }
        return jTweetsRepository.save(jTweet);
    }

    public JTweet getTweet(long id) {
        Optional<JTweet> jTweet = jTweetsRepository.findById(id);

        if (jTweet.isPresent()) {
            return jTweet.get();
        } else {
            return null;
        }
    }

    public JTweet updateTweet(long id, JTweetUpdate jTweetUpdate) {
        Optional<JTweet> jTweet = jTweetsRepository.findById(id);

        if (!jTweet.isPresent() || jTweetUpdate.getBody().equals("")) {
            throw new InvalidTweetException("Invalid update");
        } else {
            jTweet.get().setBody(jTweetUpdate.getBody());
            return jTweetsRepository.save(jTweet.get());
        }
    }

    public void deleteTweet(long id) {
        Optional<JTweet> jTweet = jTweetsRepository.findById(id);

        if (jTweet.isPresent()) {
            jTweetsRepository.delete(jTweet.get());
        } else {
            throw new InvalidTweetException("Invalid delete");
        }
    }
}
