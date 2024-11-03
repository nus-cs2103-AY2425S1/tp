package seedu.address.model.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Manages stock levels for ingredients in an inventory.
 * Provides methods to add, remove, and check ingredient stock, as well as to verify if a pastry can be made.
 */
public class Inventory {
    private final Map<Integer, Integer> stockLevels = new HashMap<>();
    private final IngredientCatalogue ingredientCatalogue;

    /**
     * Constructs an {@code Inventory} with an associated {@code IngredientCatalogue}.
     * Initializes stock levels for all ingredients to zero.
     *
     * @param ingredientCatalogue The ingredient catalogue used for retrieving ingredients.
     */
    public Inventory(IngredientCatalogue ingredientCatalogue) {
        this.ingredientCatalogue = ingredientCatalogue;
        initializeStock();
    }

    /**
     * Retrieves an ingredient by its name from the ingredient catalogue.
     *
     * @param name The name of the ingredient.
     * @return The ingredient if found.
     */
    public Ingredient getIngredientByName(String name) {
        return ingredientCatalogue.getIngredientByName(name);
    }

    /**
     * Initializes stock levels for all ingredients in the catalogue to zero.
     */
    private void initializeStock() {
        for (Product product : ingredientCatalogue.getCatalogue().values()) {
            if (product instanceof Ingredient ingredient) {
                // Initialize each ingredient in catalog to 0 unit of stock
                stockLevels.put(ingredient.getProductId(), 0);
            } else {
                throw new IllegalStateException("Ingredient catalogue contains a non-ingredient item.");
            }
        }
    }

    /**
     * Adds stock to a specified ingredient.
     *
     * @param ingredientId The ID of the ingredient to update.
     * @param quantity The quantity to add; must be non-negative.
     * @throws NoSuchElementException if the ingredient ID is not found in the inventory.
     */
    public void addStock(int ingredientId, int quantity) {
        assert quantity >= 0 : "Quantity to add must be >= 0";

        if (!stockLevels.containsKey(ingredientId)) {
            throw new NoSuchElementException("Ingredient ID " + ingredientId + " not found.");
        }
        stockLevels.put(ingredientId, stockLevels.get(ingredientId) + quantity);
        Ingredient ingredient = ingredientCatalogue.getIngredientById(ingredientId);
        System.out.printf("Added %d units to ingredient: ID: %d | %s%n",
                quantity, ingredientId, ingredient.getName());
    }

    /**
     * Removes stock from a specified ingredient.
     *
     * @param ingredientId The ID of the ingredient to update.
     * @param quantity The quantity to remove; must be greater than zero.
     * @throws IllegalArgumentException if the quantity is zero or negative or if the quantity exceeds available stock.
     * @throws NoSuchElementException if the ingredient ID is not found in the inventory.
     */
    public void removeStock(int ingredientId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to remove must be greater than 0.");
        }

        if (!stockLevels.containsKey(ingredientId)) {
            throw new NoSuchElementException("Ingredient ID " + ingredientId + " not found.");
        }

        int currentStock = stockLevels.get(ingredientId);

        if (currentStock < quantity) {
            throw new IllegalArgumentException(
                    String.format("Insufficient stock for ingredient: ID: %d | %s",
                            ingredientId, ingredientCatalogue.getIngredientById(ingredientId).getName()));
        }
        stockLevels.put(ingredientId, currentStock - quantity);
        Ingredient ingredient = ingredientCatalogue.getIngredientById(ingredientId);
        System.out.printf("Removed %d units from ingredient: ID: %d | %s%n",
                quantity, ingredientId, ingredient.getName());
    }

    /**
     * Checks if there is sufficient stock to make a specified pastry.
     *
     * @param pastry The pastry to check.
     * @return {@code true} if all required ingredients are in stock; {@code false} otherwise.
     */
    public boolean canMakePastry(Pastry pastry) {
        assert pastry != null : "Pastry cannot be null";
        List<Ingredient> ingredients = pastry.getIngredients();

        for (Ingredient ingredient : ingredients) {
            int ingredientId = ingredient.getProductId();
            int requiredStock = 1; // Assuming 1 unit of each ingredient is needed per pastry
            int currentStock = stockLevels.getOrDefault(ingredientId, 0);

            if (currentStock < requiredStock) {
                System.out.printf("Insufficient stock for ingredient: ID: %d | %s%n",
                        ingredientId, ingredient.getName());
                return false;
            }
        }
        return true;
    }

    /**
     * Displays current stock levels of all ingredients in the inventory.
     */
    public void displayStockLevels() {
        assert !stockLevels.isEmpty() : "Stock levels should not be empty";
        System.out.println("Current Ingredient Stock Levels:");
        for (Map.Entry<Integer, Integer> entry : stockLevels.entrySet()) {
            Ingredient ingredient = ingredientCatalogue.getIngredientById(entry.getKey());
            System.out.printf("ID: %d | %s: %d units%n",
                    ingredient.getProductId(), ingredient.getName(), entry.getValue());
        }
    }

    /**
     * Returns a formatted string representation of current stock levels for all ingredients.
     *
     * @return A string listing all ingredients and their stock levels.
     */
    public String getFormattedStockLevels() {
        assert !stockLevels.isEmpty() : "Stock levels should not be empty";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : stockLevels.entrySet()) {
            Ingredient ingredient = ingredientCatalogue.getIngredientById(entry.getKey());
            sb.append(String.format("ID: %d | %s: %d units%n",
                    ingredient.getProductId(), ingredient.getName(), entry.getValue()));
        }
        return sb.toString();
    }

    /**
     * Gets the current stock level for a specified ingredient.
     *
     * @param ingredientId The ID of the ingredient.
     * @return The stock level of the ingredient, or zero if it is not in stock.
     */
    public int getStockLevel(int ingredientId) {
        return stockLevels.getOrDefault(ingredientId, 0);
    }
}