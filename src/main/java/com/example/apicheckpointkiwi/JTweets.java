package com.example.apicheckpointkiwi;

import java.util.ArrayList;
import java.util.List;

public class JTweets {
    private List<JTweet> jTweetList;

    public JTweets() {
        this.jTweetList = new ArrayList<>();
    }

    public JTweets(List<JTweet> jTweetList) {
        this.jTweetList = jTweetList;
    }

    public boolean isEmpty() {
        return jTweetList.isEmpty();
    }

    public int getCount() {
        return jTweetList.size();
    }
        
}
