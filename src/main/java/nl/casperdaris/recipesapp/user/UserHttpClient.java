package nl.casperdaris.recipesapp.user;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

// Just like the REST client, but with much less boilerplate
public interface UserHttpClient {

    @GetExchange("/users")
    List<User> findAll();

    @GetExchange("/users/{id}")
    User findById(@PathVariable Integer id);
}
