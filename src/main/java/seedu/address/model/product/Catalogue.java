package seedu.address.model.product;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Abstract base class representing a product catalogue.
 * Provides a common structure for managing different types of products.
 */
public abstract class Catalogue {
    protected final Map<Integer, Product> productCatalogue = new HashMap<>();
    protected int nextProductId = 1;

    /**
     * Adds default products to the catalogue.
     * This method must be implemented by subclasses to add specific types of products.
     */
    public abstract void addDefaultProducts();

    /**
     * Retrieves a product by its unique ID.
     *
     * @param id The ID of the product.
     * @return The product with the specified ID, or {@code null} if not found.
     */
    public Product getProductById(int id) {
        return productCatalogue.get(id);
    }

    /**
     * Returns the entire catalogue of products.
     *
     * @return A map of product IDs to products.
     */
    public Map<Integer, Product> getCatalogue() {
        return productCatalogue;
    }

    /**
     * Returns the next available product ID.
     *
     * @return The next product ID as an integer.
     */
    public int getNextProductId() {
        return nextProductId;
    }

    /**
     * Deletes a product from the catalogue by its ID.
     *
     * @param id The ID of the product to be deleted.
     * @throws NoSuchElementException if the product ID does not exist in the catalogue.
     */
    public void deleteProduct(int id) {
        if (!productCatalogue.containsKey(id)) {
            throw new NoSuchElementException("Product ID " + id + " not found.");
        }
        productCatalogue.remove(id);
        System.out.println("Deleted product with ID: " + id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Product> entry : productCatalogue.entrySet()) {
            sb.append(entry.getValue().toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}