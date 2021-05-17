package com.example.apicheckpointkiwi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JTweetServiceTests {

    JTweetService jTweetService;

    @Mock
    JTweetsRepository jTweetsRepository;

    JTweet jTweet;
    List<JTweet> jTweets;
    List<String> authors = new ArrayList<String>() {
        {
            add("peter");
            add("rob");
        }
    };

    @BeforeEach
    void setUp() {
        jTweet = new JTweet(4, "kiwi", "Hello World");
        jTweetService = new JTweetService(jTweetsRepository);
        jTweets = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            jTweets.add(new JTweet(i, authors.get(i % 2), "Tweet" + i));
        }
    }

    @Test
    void getTweets_returnsTweets() {
        when(jTweetsRepository.findAll()).thenReturn(jTweets);

        assertThat(jTweetService.getTweets().isEmpty()).isFalse();
    }

    @Test
    void getTweets_noContent_returnsEmptyList() {
        when(jTweetsRepository.findAll()).thenReturn(new ArrayList<>());

        assertThat(jTweetService.getTweets().isEmpty()).isTrue();
    }

    @Test
    void getTweets_withArgs_returnsTweets() {
        when(jTweetsRepository.findByAuthorAndLocalDate(anyString(), anyString())).thenReturn(jTweets);

        assertThat(jTweetService.getTweets("kiwi", "2021-05-17").isEmpty()).isFalse();
    }

    @Test
    void getTweets_withArgs_noContent_returnsEmptyList() {
        when(jTweetsRepository.findByAuthorAndLocalDate(anyString(), anyString())).thenReturn(new ArrayList<>());

        assertThat(jTweetService.getTweets("kiwi", "2021-05-17")).isNull();
    }

    @Test
    void addTweet_returnsTweet() {
        when(jTweetsRepository.save(any(JTweet.class))).thenReturn(jTweet);

        assertThat(jTweetService.addTweet(jTweet)).isNotNull();
    }

    @Test
    void addTweet_invalidArgs_throwsInvalidTweetException() {
        assertThatThrownBy(() -> {
            jTweetService.addTweet(new JTweet(-1,"", ""));
        }).isInstanceOf(InvalidTweetException.class);
    }
}
