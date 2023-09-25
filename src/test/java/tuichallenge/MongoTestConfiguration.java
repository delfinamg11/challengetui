package tuichallenge;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Configuration
public class MongoTestConfiguration {

	private static final String IP = "localhost";
	private static final int PORT = 28017;

	@Bean
	public ImmutableMongodConfig embeddedMongoConfiguration() throws IOException {
		return MongodConfig.builder().version(Version.V3_4_15).net(new Net(IP, PORT, Network.localhostIsIPv6()))
				.build();
	}
}
