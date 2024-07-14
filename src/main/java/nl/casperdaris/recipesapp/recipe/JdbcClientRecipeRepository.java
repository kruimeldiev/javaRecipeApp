package nl.casperdaris.recipesapp.recipe;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcClientRecipeRepository {

    private final JdbcClient jdbcClient;

    public JdbcClientRecipeRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Retrieves all recipes from the database.
     *
     * @return a list of Recipe objects representing all recipes in the database.
     *         If no recipes are found, returns an empty list.
     */
    public List<Recipe> findAll() {
        return jdbcClient.sql("SELECT * FROM recipes")
                .query(Recipe.class)
                .list();
    }

    /**
     * Retrieves a recipe by its ID from the database.
     *
     * @param id the ID of the recipe to retrieve.
     * @return an Optional containing the Recipe object if found, or an empty
     *         Optional if no recipe with the specified ID exists.
     */
    public Optional<Recipe> findById(Integer id) {
        return jdbcClient.sql("SELECT id, title, description, time_in_minutes, rating FROM recipes WHERE id = :id")
                .param("id", id)
                .query(Recipe.class)
                .optional();
    }

    /**
     * Inserts a new recipe into the database.
     *
     * @param recipe the Recipe object to be inserted.
     * @return the number of rows affected by the insert operation.
     */
    public int create(Recipe recipe) {
        return jdbcClient.sql(
                "INSERT INTO recipes (id, title, description, time_in_minutes, rating) VALUES (:id, :title, :description, :timeInMinutes, :rating)")
                .param("id", recipe.id())
                .param("title", recipe.title())
                .param("description", recipe.description())
                .param("timeInMinutes", recipe.timeInMinutes())
                .param("rating", recipe.rating())
                .update();
    }

    /**
     * Updates an existing recipe in the database.
     *
     * @param recipe the Recipe object to be updated.
     * @return the number of rows affected by the update operation.
     */
    public int updateById(Recipe recipe, Integer id) {
        return jdbcClient.sql(
                "UPDATE recipes SET title = :title, description = :description, time_in_minutes = :timeInMinutes, rating = :rating WHERE id = :id")
                .param("id", id)
                .param("title", recipe.title())
                .param("description", recipe.description())
                .param("timeInMinutes", recipe.timeInMinutes())
                .param("rating", recipe.rating())
                .update();
    }

    /**
     * Deletes a recipe from the database by its ID.
     *
     * @param id the ID of the recipe to be deleted.
     * @return the number of rows affected by the delete operation.
     */
    public int deleteById(Integer id) {
        return jdbcClient.sql("DELETE FROM recipes WHERE id = :id")
                .param("id", id)
                .update();
    }

    /**
     * Counts the total number of recipes in the database.
     *
     * @return the total number of recipes.
     */
    public int count() {
        return jdbcClient.sql("SELECT COUNT(*) FROM recipes")
                .query(Integer.class)
                .single();
    }

    /**
     * Inserts multiple recipes into the database.
     *
     * @param recipes the list of Recipe objects to be inserted.
     */
    public void saveAll(List<Recipe> recipes) {
        recipes.stream().forEach(run -> create(run));
    }
}
