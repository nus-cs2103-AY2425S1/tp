package seedu.address.model.product;

import java.util.HashMap;
import java.util.Map;

public abstract class Catalogue {
    protected final Map<Integer, Product> productCatalogue = new HashMap<>();
    protected int nextProductId = 1;  // Common ID counter for all products

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
}
