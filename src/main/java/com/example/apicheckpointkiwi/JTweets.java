package com.example.apicheckpointkiwi;

import java.util.ArrayList;
import java.util.List;

public class JTweets {
    private List<JTweet> tweets;

    public JTweets() {
        this.tweets = new ArrayList<>();
    }

    public JTweets(List<JTweet> tweets) {
        this.tweets = tweets;
    }

    public List<JTweet> getTweets() {
        return this.tweets;
    }

    public boolean isEmpty() {
        return tweets.isEmpty();
    }

    public int getCount() {
        return tweets.size();
    }
        
}
