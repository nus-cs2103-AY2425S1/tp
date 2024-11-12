package seedu.address.model.product;

/**
 * Represents an ingredient, which is a specific type of product.
 * Used as a building block for other products like {@code Pastry}.
 */
public class Ingredient extends Product {
    // Default constructor for Jackson
    public Ingredient() {
        super(0, "", 0.0); // Default values for the fields
    }

    /**
     * Constructs an {@code Ingredient} with specified ID, name, and cost.
     *
     * @param productId The unique ID for the ingredient.
     * @param name The name of the ingredient.
     * @param cost The cost of the ingredient.
     */
    public Ingredient(int productId, String name, double cost) {
        super(productId, name, cost);
    }

    /**
     * Returns a string representation of the ingredient.
     *
     * @return A formatted string with ingredient details.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}