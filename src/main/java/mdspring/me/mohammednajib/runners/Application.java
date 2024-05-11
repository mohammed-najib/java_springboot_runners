package mdspring.me.mohammednajib.runners;

import org.springframework.boot.CommandLineRunner;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import mdspring.me.mohammednajib.runners.user.UserHttpClient;

@SpringBootApplication
public class Application {

	// private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	UserHttpClient userHttpClient() {
		RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
				.build();

		return factory.createClient(UserHttpClient.class);
	}

	@Bean
	// CommandLineRunner runner(UserRestClient client) {
	CommandLineRunner runner(UserHttpClient client) {
		return args -> {
			// log.info("All users: {}", client.findAll());
			// log.info("User with id 1: {}", client.findById(1));
			System.out.println("All users: " + client.findAll());
			System.out.println("User with id 1: " + client.findById(1));
		};
	}
}
