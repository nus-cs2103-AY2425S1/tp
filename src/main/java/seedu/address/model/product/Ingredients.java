package seedu.address.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a collection of Ingredients supplied by a supplier.
 */
public class Ingredients {
    private final List<Ingredient> ingredientsList;

    public Ingredients(List<Ingredient> ingredientsList) {
        this.ingredientsList = new ArrayList<>(ingredientsList);
    }

    public List<String> getIngredientNames() {
        List<String> names = new ArrayList<>();
        for (Ingredient ingredient : ingredientsList) {
            names.add(ingredient.getName());
        }
        return names;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Ingredient ingredient : ingredientsList) {
            builder.append(ingredient.toString()).append(", ");
        }
        return builder.length() > 0 ? builder.substring(0, builder.length() - 2) : "";
    }

}

