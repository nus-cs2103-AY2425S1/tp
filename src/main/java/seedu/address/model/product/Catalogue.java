package seedu.address.model.product;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class Catalogue {
    protected final Map<Integer, Product> productCatalogue = new HashMap<>();
    protected int nextProductId = 1;  // ID counter, ensures

    public abstract void addDefaultProducts();

    public Product getProductById(int id) {
        return productCatalogue.get(id);
    }

    public String getProductNameById(int id) {
        Product product = productCatalogue.get(id);
        return (product != null) ? product.getName() : null;
    }

    public int getProductIdByName(String name) {
        for (Map.Entry<Integer, Product> entry : productCatalogue.entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(name)) {
                return entry.getKey();
            }
        }
        return -1;  // Not found
    }

    public Map<Integer, Product> getCatalogue() {
        return productCatalogue;
    }

    public void deleteProduct(int id) {
        if (!productCatalogue.containsKey(id)) {
            throw new NoSuchElementException("Product ID " + id + " not found.");
        }

        productCatalogue.remove(id);
        System.out.println("Deleted product with ID: " + id);

    }

    public void setProductId(int oldId, int newId) {
        if (!productCatalogue.containsKey(oldId)) {
            throw new NoSuchElementException("Product ID " + oldId + " not found.");  // Product with old ID not found
        }
        if (productCatalogue.containsKey(newId)) {
             throw new NoSuchElementException("ID: " + newId + " is taken.");  // New ID is already in use
        }

        Product product = productCatalogue.remove(oldId);  // Remove product with old ID
        productCatalogue.put(newId, product);  // Add product with new ID
        System.out.println("ID of " + product.getName() + " changed from " + oldId + "to ID: " + newId);  // ID changed successfully
    }
}
