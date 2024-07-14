package nl.casperdaris.recipesapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.casperdaris.recipesapp.recipe.InMemoryRecipeRepository;
import nl.casperdaris.recipesapp.recipe.Recipe;
import nl.casperdaris.recipesapp.recipe.RecipeNotFoundException;

public class InMemoryRecipeRepositoryTest {

    InMemoryRecipeRepository repository;

    // Setup before the tests
    @BeforeEach
    void setup() {
        // The InMemoryRecipeRepository init is not getting called, so we need to add
        // mock data in the test itself
        repository = new InMemoryRecipeRepository();
        repository.create(new Recipe(1, "Spaghetti Bolognese", "A classic Italian pasta dish", 30, 4.5, null));
        repository.create(new Recipe(2, "Chicken Curry", "A spicy and flavorful chicken curry", 45, 4.7, null));
        repository.create(new Recipe(3, "Vegetable Stir Fry", "A quick and healthy vegetable stir fry", 20, 4.2, null));
    }

    @Test
    void shouldFindAllRuns() {
        List<Recipe> recipes = repository.findAll();
        assertEquals(3, recipes.size(), "Should have returned two recipes.");
    }

    @Test
    void shouldFindRecipeById() {
        Optional<Recipe> recipe = repository.findById(1);
        assertTrue(recipe.isPresent(), "Recipe with ID 1 should be present.");
        assertEquals("Spaghetti Bolognese", recipe.get().title(), "Title should match.");
    }

    @Test
    void shouldNotFindRecipeByInvalidId() {
        assertThrows(RecipeNotFoundException.class, () -> repository.findById(99));
    }

    @Test
    void shouldCreateNewRecipe() {
        Recipe newRecipe = new Recipe(4, "Pancakes", "Fluffy pancakes", 15, 4.8, null);
        repository.create(newRecipe);
        assertEquals(4, repository.count(), "Should have four recipes after adding one.");
        Optional<Recipe> foundRecipe = repository.findById(4);
        assertTrue(foundRecipe.isPresent(), "New recipe should be present.");
        assertEquals("Pancakes", foundRecipe.get().title(), "Title should match.");
    }

    @Test
    void shouldUpdateRecipe() {
        Recipe updatedRecipe = new Recipe(1, "Spaghetti Bolognese", "An updated description", 35, 4.5, null);
        repository.update(1, updatedRecipe);
        Optional<Recipe> recipe = repository.findById(1);
        assertTrue(recipe.isPresent(), "Updated recipe should be present.");
        assertEquals("An updated description", recipe.get().description(), "Description should match.");
        assertEquals(35, recipe.get().timeInMinutes(), "Time should match.");
    }

    @Test
    void shouldDeleteRecipe() {
        repository.delete(1);
        assertEquals(2, repository.count(), "Should have two recipes after deletion.");
        assertThrows(RecipeNotFoundException.class, () -> repository.findById(1));
    }

    @Test
    void shouldCountRecipes() {
        assertEquals(3, repository.count(), "Should have three recipes.");
    }

    @Test
    void shouldSaveAllRecipes() {
        List<Recipe> newRecipes = List.of(
                new Recipe(4, "Pancakes", "Fluffy pancakes", 15, 4.8, null),
                new Recipe(5, "Omelette", "A simple omelette", 10, 4.4, null));
        repository.saveAll(newRecipes);
        assertEquals(5, repository.count(), "Should have five recipes after saving all.");
    }

    @Test
    void shouldFindByRating() {
        List<Recipe> recipes = repository.findByRating(4.5);
        assertEquals(1, recipes.size(), "Should have one recipe with rating 4.5.");
        assertEquals("Spaghetti Bolognese", recipes.get(0).title(), "Title should match.");
    }
}
