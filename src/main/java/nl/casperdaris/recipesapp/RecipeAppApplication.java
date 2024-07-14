package nl.casperdaris.recipesapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

import nl.casperdaris.recipesapp.user.User;
import nl.casperdaris.recipesapp.user.UserHttpClient;
import nl.casperdaris.recipesapp.user.UserRestClient;

@SpringBootApplication
public class RecipeAppApplication {

	private static final Logger log = LoggerFactory.getLogger(RecipeAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RecipeAppApplication.class, args);
	}

	@Bean
	UserHttpClient userHttpClient() {
		RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com/");
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
				.build();
		return factory.createClient(UserHttpClient.class);
	}

	@Bean
	CommandLineRunner tunner(UserHttpClient client) {
		return args -> {
			User users = client.findById(1);
			System.out.println(users);
		};
	}

}
