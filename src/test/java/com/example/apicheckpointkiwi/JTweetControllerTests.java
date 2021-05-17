package com.example.apicheckpointkiwi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JTweetController.class)
public class JTweetControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JTweetService jTweetService;

    JTweet jTweet;
    List<JTweet> jTweets;
    List<String> authors = new ArrayList<String>() {
        {
            add("peter");
            add("rob");
        }
    };
    JTweetUpdate jTweetUpdate = new JTweetUpdate("Hello Spring");
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        jTweet = new JTweet(4, "kiwi", "Hello World");
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

    @Test
    void postTweet_returnsTweet() throws Exception {
        when(jTweetService.addTweet(any(JTweet.class))).thenReturn(jTweet);

        mockMvc.perform(post("/tweets").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(jTweet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4));
    }

    @Test
    void postTweet_invalidArg_returns400() throws Exception {
        when(jTweetService.addTweet(any(JTweet.class))).thenThrow(InvalidTweetException.class);

        mockMvc.perform(post("/tweets").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(jTweet)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTweet_returnsTweet() throws Exception {
        when(jTweetService.getTweet(anyLong())).thenReturn(jTweet);

        mockMvc.perform(get("/tweets/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4));
    }

    @Test
    void getTweet_noContent_returns204() throws Exception {
        when(jTweetService.getTweet(anyLong())).thenReturn(null);

        mockMvc.perform(get("/tweets/4"))
                .andExpect(status().isNoContent());
    }
}
