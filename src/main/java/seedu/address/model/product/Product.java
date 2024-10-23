package seedu.address.model.product;

import java.util.Objects;

public abstract class Product {
    private final int productId;
    private final String name;
    private final double cost;  // New cost field

    public Product(int productId, String name, double cost) {
        this.productId = productId;
        this.name = name;
        this.cost = cost;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Product)) return false;
        Product otherProduct = (Product) other;
        return productId == otherProduct.productId && name.equals(otherProduct.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, cost);
    }

    @Override
    public String toString() {
        return String.format("Product ID: %d\n     Name: %s\n     Cost: $%.2f", productId, name, cost);
    }

    public String toShortString() {
        return String.format("%d, %s, $%.2f", productId, name, cost);
    }

    public String viewProduct() {
        return String.format("%s. Cost: $%.2f", name, cost);
    }

}
