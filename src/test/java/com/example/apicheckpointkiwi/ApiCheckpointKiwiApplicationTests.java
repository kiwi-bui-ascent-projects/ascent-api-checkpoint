package com.example.apicheckpointkiwi;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

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
	JTweet jTweet = new JTweet(4, "kiwi", "Hello World");


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
		assertThat(response.getBody()).isNull();
	}

	// Query needs to be changed to current date to pass this test
	@Test
	void getAutos_withArgs_returnsTweets() {
		ResponseEntity<JTweets> response = testRestTemplate.getForEntity("/tweets?author=rob&date=2021-05-26",
				JTweets.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getTweets().size()).isEqualTo(5);
	}

	@Test
	void getAutos_withArgs_noContent_returns204() {
		jTweetsRepository.deleteAll();

		ResponseEntity<JTweets> response = testRestTemplate.getForEntity("/tweets?author=rob&date=2021-05-26",
				JTweets.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		assertThat(response.getBody()).isNull();
	}

	@Test
	void postTweet_returnsTweet() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<JTweet> request = new HttpEntity<>(jTweet, headers);

		ResponseEntity<JTweet> response = testRestTemplate.postForEntity("/tweets", request, JTweet.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getAuthor()).isEqualTo("kiwi");
	}

	@Test
	void postTweet_invalidArgs_returns400() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<JTweet> request = new HttpEntity<>(new JTweet(), headers);

		ResponseEntity<JTweet> response = testRestTemplate.postForEntity("/tweets", request, JTweet.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
}
