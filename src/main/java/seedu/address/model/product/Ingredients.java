package seedu.address.model.product;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of ingredients provided by a supplier.
 * Allows access to ingredient details and names.
 */
public class Ingredients {
    private final List<Ingredient> ingredientsList;

    /**
     * Constructs an {@code Ingredients} collection with the provided list of ingredients.
     *
     * @param ingredientsList A list of ingredients to be managed by this class.
     */
    public Ingredients(List<Ingredient> ingredientsList) {
        this.ingredientsList = new ArrayList<>(ingredientsList);
    }

    /**
     * Retrieves the names of all ingredients in this collection.
     *
     * @return A list of ingredient names.
     */
    public List<String> getIngredientNames() {
        List<String> names = new ArrayList<>();
        for (Ingredient ingredient : ingredientsList) {
            names.add(ingredient.getName());
        }
        return names;
    }

    /**
     * Returns a formatted string representation of all ingredients in the collection.
     *
     * @return A string with details of each ingredient, separated by commas.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Ingredient ingredient : ingredientsList) {
            builder.append(ingredient.toString()).append(", ");
        }
        if (!builder.isEmpty()) {
            return builder.substring(0, builder.length() - 2);
        } else {
            return "";
        }
    }
}

