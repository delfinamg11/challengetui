package tuichallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("tuichallenge.repository")
public class TuichallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TuichallengeApplication.class, args);
	}

}
