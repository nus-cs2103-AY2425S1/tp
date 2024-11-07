package seedu.address.model.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents a pastry product with a list of ingredients.
 * Extends {@code Product} to include specific attributes for a pastry.
 */
public class Pastry extends Product {
    private final ArrayList<Ingredient> ingredients;

    /**
     * Default constructor for Jackson.
     * Initializes the Pastry object with default values.
     */
    public Pastry() {
        super(0, "", 0.0); // Set default values for fields
        this.ingredients = new ArrayList<>(); // Initialize empty list
    }

    /**
     * Constructs a {@code Pastry} object with specified ID, name, cost, and ingredients list.
     *
     * @param productId   The unique ID for the pastry.
     * @param name        The name of the pastry.
     * @param cost        The cost of the pastry.
     * @param ingredients The list of ingredients required for the pastry.
     */
    public Pastry(int productId, String name, double cost, ArrayList<Ingredient> ingredients) {
        super(productId, name, cost);
        this.ingredients = new ArrayList<>(ingredients);
        Collections.sort(this.ingredients, Comparator.comparingInt(Ingredient::getProductId));
    }

    /**
     * Returns a list of ingredients used in the pastry.
     *
     * @return A copy of the ingredients list.
     */
    public ArrayList<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    /**
     * Returns a formatted string representation of the pastry, including details of each ingredient.
     *
     * @return A string with pastry details and its ingredients.
     */
    @Override
    public String toString() {
        StringBuilder ingredientsString = new StringBuilder();
        Collections.sort(ingredients, Comparator.comparingInt(Ingredient::getProductId));
        for (Ingredient ingredient : ingredients) {
            ingredientsString.append("          ")
                    .append(ingredient.toShortString())
                    .append("\n");
        }
        return super.toString() + "\n     Ingredients: \n" + ingredientsString;
    }
}