package seedu.address.model.product;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import seedu.address.model.util.SampleDataUtil;
/**
 * Catalogue for managing ingredients. Provides methods to add and retrieve ingredients
 * by ID or name, and initializes with a set of default ingredients if no data is loaded.
 */
public class IngredientCatalogue extends Catalogue {
    private static IngredientCatalogue instance; // Singleton instance
    private final Map<String, Ingredient> ingredientByName = new HashMap<>();

    /**
     * Initializes the ingredient catalogue with provided ingredients.
     * If no ingredients are provided, initializes with default ingredients.
     */
    // New constructor to initialize with a given Map of ingredients
    public IngredientCatalogue(Map<Integer, Ingredient> ingredients) {
        super(); // Call to initialize productCatalogue in the parent class
        for (Ingredient ingredient : ingredients.values()) {
            addIngredient(ingredient);
        }
        setNextProductId(); // Initialize nextProductId based on highest ID
    }

    // Existing no-arg constructor remains for default initialization
    private IngredientCatalogue() {
        this(SampleDataUtil.getDefaultIngredients());
    }

    /**
     * Sets nextProductId to the highest ID in productCatalogue + 1.
     */
    private void setNextProductId() {
        if (!productCatalogue.isEmpty()) {
            nextProductId = Collections.max(productCatalogue.keySet()) + 1;
        }
    }
    /**
     * Returns the singleton instance of IngredientCatalogue.
     *
     * @return The singleton instance.
     */
    public static IngredientCatalogue getInstance() {
        if (instance == null) {
            instance = new IngredientCatalogue();
            instance.setNextProductId(); // Ensure nextProductId is set after initialization
        }
        return instance;
    }
    /**
     * Initializes with default ingredients.
     */
    private void initializeWithDefaultIngredients() {
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
        if (ingredient.getProductId() >= nextProductId) {
            nextProductId = ingredient.getProductId() + 1;
        }
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
        System.out.println("Looking up ingredient: " + name);
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
