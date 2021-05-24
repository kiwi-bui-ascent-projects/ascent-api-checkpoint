package com.example.apicheckpointkiwi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiCheckpointKiwiApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	JTweetsRepository jTweetsRepository;

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

		jTweetsRepository.saveAll(jTweets);
	}

	@AfterEach
	void tearDown() {
		jTweetsRepository.deleteAll();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void getAutos_noArgs_returnsTweets() {
		ResponseEntity<JTweets> response = testRestTemplate.getForEntity("/tweets", JTweets.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().isEmpty()).isFalse();
	}

	@Test
	void getAutos_noArgs_noContent_returns204() {
		jTweetsRepository.deleteAll();

		ResponseEntity<JTweets> response = testRestTemplate.getForEntity("/tweets", JTweets.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	void getAutos_withArgs_returnsTweets() {
		ResponseEntity<JTweets> response = testRestTemplate.getForEntity("/tweets?author=rob&date=2021-05-23",
				JTweets.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getTweets().size()).isEqualTo(5);
	}
}
