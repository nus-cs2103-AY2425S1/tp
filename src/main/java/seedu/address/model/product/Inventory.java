package seedu.address.model.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Inventory {
    private final Map<Integer, Integer> stockLevels = new HashMap<>();
    private final IngredientCatalogue ingredientCatalogue;

    // Constructor
    public Inventory(IngredientCatalogue ingredientCatalogue) {
        this.ingredientCatalogue = ingredientCatalogue;
        initializeStock();
    }

    // Retrieves an ingredient by name from the ingredient catalogue
    public Ingredient getIngredientByName(String name) {
        return ingredientCatalogue.getIngredientByName(name);
    }

    // Initializes stock levels for all ingredients to 0
    private void initializeStock() {
        for (Product product : ingredientCatalogue.getCatalogue().values()) {
            if (product instanceof Ingredient ingredient) {
                // Initialize each ingredient in catalog to 0 unit of stock
                stockLevels.put(ingredient.getProductId(), 0);
            } else {
                throw new IllegalStateException("Why ingredient catalogue contains non-ingredient sia?");
            }
        }
    }

    public void addStock(int ingredientId, int quantity) {
        assert quantity >= 0 : "Quantity to add must be >= 0";  // Assertion

        if (!stockLevels.containsKey(ingredientId)) {
            throw new NoSuchElementException("Ingredient ID " + ingredientId + " not found.");
        }
        stockLevels.put(ingredientId, stockLevels.get(ingredientId) + quantity);
        Ingredient ingredient = ingredientCatalogue.getIngredientById(ingredientId);
        System.out.printf("Added %d units to ingredient: ID: %d | %s%n",
                quantity, ingredientId, ingredient.getName());
    }

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

    public boolean canMakePastry(Pastry pastry) {
        assert pastry != null : "Pastry cannot be null";  // Assertion
        List<Ingredient> ingredients = pastry.getIngredients();

        for (Ingredient ingredient : ingredients) {
            int ingredientId = ingredient.getProductId();
            int requiredStock = 1;  // Assuming 1 unit of each ingredient is needed per pastry
            int currentStock = stockLevels.getOrDefault(ingredientId, 0);

            if (currentStock < requiredStock) {
                System.out.printf("Insufficient stock for ingredient: ID: %d | %s%n",
                        ingredientId, ingredient.getName());
                return false;
            }
        }
        return true;
    }

    public void displayStockLevels() {
        assert !stockLevels.isEmpty() : "Stock levels should not be empty";  // Assertion
        System.out.println("Current Ingredient Stock Levels:");
        for (Map.Entry<Integer, Integer> entry : stockLevels.entrySet()) {
            Ingredient ingredient = ingredientCatalogue.getIngredientById(entry.getKey());
            System.out.printf("ID: %d | %s: %d units%n",
                    ingredient.getProductId(), ingredient.getName(), entry.getValue());
        }
    }

    public String getFormattedStockLevels() {
        assert !stockLevels.isEmpty() : "Stock levels should not be empty";  // Assertion
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : stockLevels.entrySet()) {
            Ingredient ingredient = ingredientCatalogue.getIngredientById(entry.getKey());
            sb.append(String.format("ID: %d | %s: %d units%n",
                    ingredient.getProductId(), ingredient.getName(), entry.getValue()));
        }
        return sb.toString();
    }

    public int getStockLevel(int ingredientId) {
        return stockLevels.getOrDefault(ingredientId, 0);
    }
}