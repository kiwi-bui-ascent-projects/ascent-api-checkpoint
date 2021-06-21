package com.example.apicheckpointkiwi.service;

import com.example.apicheckpointkiwi.exception.InvalidTweetException;
import com.example.apicheckpointkiwi.model.JTweet;
import com.example.apicheckpointkiwi.model.JTweetUpdate;
import com.example.apicheckpointkiwi.repository.JTweetsRepository;
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
import static org.mockito.Mockito.verify;
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
    long id = 4;
    JTweetUpdate jTweetUpdate = new JTweetUpdate("This tweet has been updated");

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
    void getTweets_no_Args_returnsTweets() {
        when(jTweetsRepository.findAll()).thenReturn(jTweets);

        assertThat(jTweetService.getTweets().isEmpty()).isFalse();
    }

    @Test
    void getTweets_noArgs_noContent_returnsEmptyList() {
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

        assertThat(jTweetService.getTweets("kiwi", "2021-05-17").isEmpty()).isTrue();
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

    @Test
    void getTweet_returnsTweet() {
        when(jTweetsRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(jTweet));

        assertThat(jTweetService.getTweet(id)).isNotNull();
    }

    @Test
    void getTweet_noContent_returnsNull() {
        when(jTweetsRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThat(jTweetService.getTweet(id)).isNull();
    }

    @Test
    void updateTweet_returnsTweet() {
        when(jTweetsRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(jTweet));
        when(jTweetsRepository.save(any(JTweet.class))).thenReturn(jTweet);

        assertThat(jTweetService.updateTweet(id, jTweetUpdate)).isNotNull();
    }

    @Test
    void updateTweet_badRequest_throwsInvalidTweetException() {
        assertThatThrownBy(() -> {
            jTweetService.updateTweet(1, new JTweetUpdate(""));
        }).isInstanceOf(InvalidTweetException.class);
    }

    @Test
    void deleteTweet_success() {
        when(jTweetsRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(jTweet));

        jTweetService.deleteTweet(id);

        verify(jTweetsRepository).delete(any(JTweet.class));
    }

    @Test
    void deleteTweet_noContent_throwsInvalidTweetException() {
        when(jTweetsRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> {
            jTweetService.deleteTweet(id);
        }).isInstanceOf(InvalidTweetException.class);
    }
}
