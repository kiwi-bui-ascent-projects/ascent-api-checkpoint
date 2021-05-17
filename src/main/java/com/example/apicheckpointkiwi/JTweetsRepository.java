package com.example.apicheckpointkiwi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JTweetsRepository extends JpaRepository<JTweet, Long> {
}
