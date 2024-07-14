package nl.casperdaris.recipesapp.recipe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RecipeJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RecipeJsonDataLoader.class);
    private final JdbcClientRecipeRepository recipeRepository;
    private final ObjectMapper objectMapper;

    public RecipeJsonDataLoader(JdbcClientRecipeRepository recipeRepository, ObjectMapper objectMapper) {
        this.recipeRepository = recipeRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (recipeRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/recipe.json")) {
                Recipe[] recipesArray = objectMapper.readValue(inputStream, Recipe[].class);
                List<Recipe> recipesList = Arrays.asList(recipesArray);
                log.info("Reading {} runs from JSON data and saving to in-memory collection.",
                        recipesList.size());
                recipeRepository.saveAll(recipesList);
            } catch (IOException e) {
                throw new Exception("Failed to read JSON data:", e);
            }
        } else {
            log.info("Not loading recipes from JSON data because the collection contains data.");
        }
    }

}
