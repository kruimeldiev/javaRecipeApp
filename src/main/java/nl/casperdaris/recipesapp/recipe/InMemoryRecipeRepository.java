package nl.casperdaris.recipesapp.recipe;

import jakarta.annotation.PostConstruct;

import java.util.*;

public class InMemoryRecipeRepository {

    private final List<Recipe> recipes = new ArrayList<>();

    public List<Recipe> findAll() {
        return recipes;
    }

    public Optional<Recipe> findById(Integer id) {
        return Optional.ofNullable(recipes.stream()
                .filter(recipe -> recipe.id() == id)
                .findFirst()
                .orElseThrow(RecipeNotFoundException::new));
    }

    public void create(Recipe recipe) {
        Recipe newRecipe = new Recipe(recipe.id(), recipe.title(), recipe.description(), recipe.timeInMinutes(),
                recipe.rating(), null);
        recipes.add(newRecipe);
    }

    public void update(Integer id, Recipe updatedRecipe) {
        Optional<Recipe> existingRecipeOptional = findById(id);
        if (existingRecipeOptional.isPresent()) {
            Recipe existingRecipe = existingRecipeOptional.get();
            recipes.remove(existingRecipe);
            Recipe newRecipe = new Recipe(id, updatedRecipe.title(), updatedRecipe.description(),
                    updatedRecipe.timeInMinutes(), updatedRecipe.rating(), null);
            recipes.add(newRecipe);
        } else {
            throw new RecipeNotFoundException();
        }
    }

    public void delete(Integer id) {
        Optional<Recipe> existingRecipeOptional = findById(id);
        if (existingRecipeOptional.isPresent()) {
            recipes.remove(existingRecipeOptional.get());
        } else {
            throw new RecipeNotFoundException();
        }
    }

    public long count() {
        return recipes.size();
    }

    public void saveAll(List<Recipe> recipesToSave) {
        for (Recipe recipe : recipesToSave) {
            create(recipe);
        }
    }

    public List<Recipe> findByRating(double rating) {
        return recipes.stream()
                .filter(recipe -> Objects.equals(recipe.rating(), rating))
                .toList();
    }

    @PostConstruct
    private void init() {
        Recipe recipe1 = new Recipe(1, "Spaghetti Bolognese", "A classic Italian pasta dish", 30, 4.5, null);
        Recipe recipe2 = new Recipe(2, "Chicken Curry", "A spicy and flavorful chicken curry", 45, 4.7, null);
        Recipe recipe3 = new Recipe(3, "Vegetable Stir Fry", "A quick and healthy vegetable stir fry", 20, 4.2, null);
        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);
    }
}