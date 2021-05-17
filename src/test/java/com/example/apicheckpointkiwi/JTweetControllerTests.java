package com.example.apicheckpointkiwi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(JTweetController.class)
public class JTweetControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JTweetService jTweetService;

    List<JTweet> jTweets;

    @BeforeEach
    void setUp() {
        jTweets = new ArrayList<>();
    }

    @Test
    void getTweets_noArgs_returnsTweets() {
    }
}
