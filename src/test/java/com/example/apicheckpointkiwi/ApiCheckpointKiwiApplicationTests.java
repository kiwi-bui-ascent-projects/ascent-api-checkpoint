package com.example.apicheckpointkiwi;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiCheckpointKiwiApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	JTweetsRepository jTweetsRepository;

	private static HttpHeaders headers;
	private static JTweet jTweet;
	private static final List<String> authors = new ArrayList<String>();

	List<JTweet> jTweets;
	Long id;
	HttpEntity<JTweetUpdate> request;

	@BeforeAll
	public static void before() {
		headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		jTweet = new JTweet(4, "kiwi", "Hello World");

		authors.add("peter");
		authors.add("rob");
	}

	@BeforeEach
	void setUp() {
		jTweets = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			jTweets.add(new JTweet(authors.get(i % 2), "Tweet" + i));
		}

		jTweets = jTweetsRepository.saveAll(jTweets);
		id = jTweets.get(0).getId();
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

	@Test
	void getAutos_withArgs_returnsTweets() {
		ResponseEntity<JTweets> response = testRestTemplate.getForEntity("/tweets?author=rob&date=" +
						LocalDate.now(), JTweets.class);

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
		HttpEntity<JTweet> request = new HttpEntity<>(jTweet, headers);

		ResponseEntity<JTweet> response = testRestTemplate.postForEntity("/tweets", request, JTweet.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getAuthor()).isEqualTo("kiwi");
	}

	@Test
	void postTweet_invalidArgs_returns400() {
		HttpEntity<JTweet> request = new HttpEntity<>(new JTweet(), headers);

		ResponseEntity<JTweet> response = testRestTemplate.postForEntity("/tweets", request, JTweet.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void getTweet_returnsTweet() {
		ResponseEntity<JTweet> response = testRestTemplate.getForEntity("/tweets/" + id, JTweet.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getAuthor()).isEqualTo("peter");
	}

	@Test
	void getTweet_noContent_returns204() {
		ResponseEntity<JTweet> response = testRestTemplate.getForEntity("/tweets/999", JTweet.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	void patchTweet_returnsTweet() {
		request = new HttpEntity<>(new JTweetUpdate("Hello Spring"), headers);

		ResponseEntity<JTweet> response = testRestTemplate.exchange("/tweets/" + id, HttpMethod.PATCH,
				request, JTweet.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getBody()).isEqualTo("Hello Spring");
	}

	@Test
	void patchTweet_invalidArgs_returns400() {
		request = new HttpEntity<>(new JTweetUpdate(""), headers);

		ResponseEntity<JTweet> response = testRestTemplate.exchange("/tweets/" + id, HttpMethod.PATCH,
				request, JTweet.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void deleteTweet_returns202() {
		ResponseEntity<Void> response = testRestTemplate.exchange("/tweets/" + id, HttpMethod.DELETE,
				new HttpEntity<>(new HttpHeaders()), Void.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
	}
}
