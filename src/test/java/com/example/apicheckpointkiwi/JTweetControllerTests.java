package com.example.apicheckpointkiwi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JTweetController.class)
public class JTweetControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JTweetService jTweetService;

    List<JTweet> jTweets;
    List<String> authors = new ArrayList<String>() {
        {
            add("peter");
            add("rob");
        }
    };

    @BeforeEach
    void setUp() {
        jTweets = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            jTweets.add(new JTweet(i, authors.get(i % 2), "Tweet" + i));
        }
    }

    @Test
    void getTweets_noArgs_returnsTweets() throws Exception {
        when(jTweetService.getTweets()).thenReturn(new JTweets(jTweets));

        mockMvc.perform(get("/tweets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tweets", hasSize(10)));
    }

    @Test
    void getTweets_noArgs_noContent_returns204() throws Exception {
        when(jTweetService.getTweets()).thenReturn(new JTweets());

        mockMvc.perform(get("/tweets"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getTweets_withArgs_returnsTweets() throws Exception {
        when(jTweetService.getTweets(anyString(), anyString())).thenReturn(new JTweets(jTweets));

        mockMvc.perform(get("/tweets?author=rob&date=2021-05-17"))
                .andExpect(status().isOk());
    }

    @Test
    void getTweets_withArgs_noContent_returns204() throws Exception {
        when(jTweetService.getTweets(anyString(), anyString())).thenReturn(new JTweets(jTweets));

        mockMvc.perform(get("/tweets?author=rob&date=2021-05-17"))
                .andExpect(status().isOk());
    }
}
