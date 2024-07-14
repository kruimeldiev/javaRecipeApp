package nl.casperdaris.recipesapp.recipe;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

public interface RecipeRepository extends ListCrudRepository<Recipe, Integer> {

    List<Recipe> findAllByRating(double rating);
}