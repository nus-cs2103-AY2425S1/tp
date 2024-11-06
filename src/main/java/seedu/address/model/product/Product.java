package seedu.address.model.product;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Ingredient.class, name = "Ingredient"),
        @JsonSubTypes.Type(value = Pastry.class, name = "Pastry")
})

/**
 * Represents a generic product with a unique ID, name, and cost.
 * This class is abstract and intended to be subclassed by specific types of products.
 */
public abstract class Product {
    private final int productId;
    private final String name;
    private final double cost;

    /**
     * Constructs a {@code Product} with the specified ID, name, and cost.
     *
     * @param productId The unique identifier for the product.
     * @param name The name of the product.
     * @param cost The cost of the product.
     */
    public Product(int productId, String name, double cost) {
        this.productId = productId;
        this.name = name;
        this.cost = cost;
    }

    /**
     * Returns the product's unique ID.
     *
     * @return The product ID.
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Returns the product's name.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the cost of the product.
     *
     * @return The product's cost as a double.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Checks if this product is equal to another object.
     * Two products are considered equal if they have the same ID and name.
     *
     * @param other The object to compare with.
     * @return {@code true} if the other object is a {@code Product} with the same ID and name; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Product otherProduct) {
            return productId == otherProduct.productId && name.equals(otherProduct.name);
        } else {
            return false;
        }
    }

    /**
     * Returns a formatted string with detailed information about the product, including its ID, name, and cost.
     *
     * @return A detailed string representation of the product.
     */
    @Override
    public String toString() {
        return String.format("Product ID: %d\n     Name: %s\n     Cost: $%.2f\n", productId, name, cost);
    }

    /**
     * Returns a short, comma-separated string with the product's ID, name, and cost.
     *
     * @return A concise string representation of the product.
     */
    public String toShortString() {
        return String.format("%d, %s, $%.2f", productId, name, cost);
    }

    /**
     * Returns a user-friendly view of the product's name and cost.
     *
     * @return A string with the product's name and cost.
     */
    public String viewProduct() {
        return String.format("%s. Cost: $%.2f", name, cost);
    }
}