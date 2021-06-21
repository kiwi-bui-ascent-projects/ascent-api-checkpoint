package com.example.apicheckpointkiwi.repository;

import com.example.apicheckpointkiwi.model.JTweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JTweetsRepository extends JpaRepository<JTweet, Long> {
    List<JTweet> findByAuthorAndLocalDate(String author, String localDate);
}
