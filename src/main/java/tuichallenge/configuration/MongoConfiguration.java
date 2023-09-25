package tuichallenge.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("tuichallenge.repository")
public class MongoConfiguration {

}
