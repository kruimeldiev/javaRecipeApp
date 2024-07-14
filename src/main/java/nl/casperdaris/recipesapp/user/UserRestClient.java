package nl.casperdaris.recipesapp.user;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

// Fetches user mock data
@Component
public class UserRestClient {
    private final RestClient restClient;

    public UserRestClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://jsonplaceholder.typicode.com/").build();
    }

    public List<User> findAll() {
        return restClient.get().uri("/users").retrieve().body(new ParameterizedTypeReference<>() {
        });
    }

    public User findById(Integer id) {
        return restClient.get().uri("/users/{id}", id).retrieve().body(User.class);

    }
}
