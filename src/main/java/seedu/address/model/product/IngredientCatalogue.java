package seedu.address.model.product;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Catalogue for managing ingredients.
 */
public class IngredientCatalogue extends Catalogue {
    // Static references to default ingredients for direct access
    public static final Ingredient FLOUR = new Ingredient(1, "Flour", 1.50);
    public static final Ingredient SUGAR = new Ingredient(2, "Sugar", 0.80);
    public static final Ingredient STRAWBERRY = new Ingredient(3, "Strawberry", 3.00);
    public static final Ingredient CHOCOLATE = new Ingredient(4, "Chocolate", 2.50);
    public static final Ingredient CHEESE = new Ingredient(5, "Cheese", 4.00);
    public static final Ingredient CREAM = new Ingredient(6, "Cream", 2.00);

    // Map to store ingredient names for quick lookups
    private final Map<String, Ingredient> ingredientByName = new HashMap<>();

    public IngredientCatalogue() {
        addDefaultProducts();
    }

    @Override
    public void addDefaultProducts() {
        // add 6 default ingredients
        addIngredient(FLOUR);
        addIngredient(SUGAR);
        addIngredient(STRAWBERRY);
        addIngredient(CHOCOLATE);
        addIngredient(CHEESE);
        addIngredient(CREAM);
    }

    public int getNextProductId() {
        return nextProductId;
    }

    public void addIngredient(Ingredient ingredient) {
        productCatalogue.put(ingredient.getProductId(), ingredient);
        ingredientByName.put(ingredient.getName().toLowerCase(), ingredient);  // Store by lowercase name
        nextProductId++;
    }

    /**
     * Retrieves an ingredient by its name (case-insensitive).
     * @param name The name of the ingredient.
     * @return The matching Ingredient object.
     * @throws NoSuchElementException if the ingredient is not found.
     */
    public Ingredient getIngredientByName(String name) {
        Ingredient ingredient = ingredientByName.get(name.toLowerCase());
        if (ingredient == null) {
            throw new NoSuchElementException("Ingredient with name '" + name + "' not found.");
        }
        return ingredient;
    }

    // Retrieves an ingredient by its ID
    public Ingredient getIngredientById(int id) {
        Product product = productCatalogue.get(id);
        if (product instanceof Ingredient) {
            return (Ingredient) product;
        }
        throw new NoSuchElementException("Ingredient with ID " + id + " not found.");
    }

}