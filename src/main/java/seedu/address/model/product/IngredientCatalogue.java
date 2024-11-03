package seedu.address.model.product;

import seedu.address.model.util.SampleDataUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Catalogue for managing ingredients. Provides methods to add and retrieve ingredients
 * by ID or name, and initializes with a set of default ingredients.
 */
public class IngredientCatalogue extends Catalogue {

    private final Map<String, Ingredient> ingredientByName = new HashMap<>();

    /**
     * Initializes the ingredient catalogue with default ingredients.
     */
    public IngredientCatalogue() {
        // Populate catalogue with default ingredients from SampleDataUtil
        Map<Integer, Ingredient> defaultIngredients = SampleDataUtil.getDefaultIngredients();
        for (Ingredient ingredient : defaultIngredients.values()) {
            addIngredient(ingredient);
        }
    }

    /**
     * Adds an ingredient to the catalogue and maps it by name for quick retrieval.
     *
     * @param ingredient The ingredient to add.
     */
    public void addIngredient(Ingredient ingredient) {
        productCatalogue.put(ingredient.getProductId(), ingredient);
        ingredientByName.put(ingredient.getName().toLowerCase(), ingredient);
        nextProductId++;
    }

    /**
     * Retrieves an ingredient by its name, case-insensitive.
     *
     * @param name The name of the ingredient.
     * @return The matching {@code Ingredient} object.
     * @throws NoSuchElementException if the ingredient is not found.
     */
    public Ingredient getIngredientByName(String name) {
        Ingredient ingredient = ingredientByName.get(name.toLowerCase());
        if (ingredient == null) {
            throw new NoSuchElementException("Ingredient with name '" + name + "' not found.");
        }
        return ingredient;
    }

    /**
     * Retrieves an ingredient by its unique ID.
     *
     * @param id The ID of the ingredient.
     * @return The matching {@code Ingredient} object.
     * @throws NoSuchElementException if the ingredient is not found.
     */
    public Ingredient getIngredientById(int id) {
        Product product = productCatalogue.get(id);
        if (product instanceof Ingredient) {
            return (Ingredient) product;
        }
        throw new NoSuchElementException("Ingredient with ID " + id + " not found.");
    }
}

