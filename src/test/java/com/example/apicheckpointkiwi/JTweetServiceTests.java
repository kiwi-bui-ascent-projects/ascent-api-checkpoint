package com.example.apicheckpointkiwi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JTweetServiceTests {

    JTweetService jTweetService;

    @Mock
    JTweetsRepository jTweetsRepository;

    JTweets jTweets;

    @BeforeEach
    void setUp() {
        jTweetService = new JTweetService(jTweetsRepository);

        jTweets = null;
    }

    @Test
    void getTweets_returnsTweets() {
        JTweets jTweets = jTweetService.getTweets();

        assertThat(jTweets).isNotNull();
    }

    @Test
    void getTweets_noContent_returnsEmptyList() {
        when(jTweetsRepository.findAll()).thenReturn(new ArrayList<>());

        assertThat(jTweetService.getTweets().isEmpty()).isTrue();
    }
}
