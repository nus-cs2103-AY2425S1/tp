package seedu.address.model.product;

import seedu.address.model.util.SampleDataUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Catalogue for managing ingredients.
 */
public class IngredientCatalogue extends Catalogue {

    private final Map<String, Ingredient> ingredientByName = new HashMap<>();

    public IngredientCatalogue() {
        // Populate catalogue with default ingredients from SampleDataUtil
        Map<Integer, Ingredient> defaultIngredients = SampleDataUtil.getDefaultIngredients();
        for (Ingredient ingredient : defaultIngredients.values()) {
            addIngredient(ingredient);
        }
    }

    public int getNextProductId() {
        return nextProductId;
    }

    public void addIngredient(Ingredient ingredient) {
        productCatalogue.put(ingredient.getProductId(), ingredient);
        ingredientByName.put(ingredient.getName().toLowerCase(), ingredient);
        nextProductId++;
    }

    public Ingredient getIngredientByName(String name) {
        Ingredient ingredient = ingredientByName.get(name.toLowerCase());
        if (ingredient == null) {
            throw new NoSuchElementException("Ingredient with name '" + name + "' not found.");
        }
        return ingredient;
    }

    public Ingredient getIngredientById(int id) {
        Product product = productCatalogue.get(id);
        if (product instanceof Ingredient) {
            return (Ingredient) product;
        }
        throw new NoSuchElementException("Ingredient with ID " + id + " not found.");
    }
}
