package seedu.address.model.product;

import seedu.address.model.util.SampleDataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Catalogue for managing pastries.
 */
public class PastryCatalogue extends Catalogue {

    public PastryCatalogue() {
        // Populate catalogue with default pastries from SampleDataUtil
        List<Pastry> defaultPastries = SampleDataUtil.getDefaultPastries();
        for (Pastry pastry : defaultPastries) {
            addPastry(pastry.getName(), pastry.getCost(), pastry.getIngredients());
        }
    }

    public int getNextProductId() {
        return nextProductId;
    }

    public void addPastry(String name, double cost, ArrayList<Ingredient> ingredients) {
        Pastry pastry = new Pastry(nextProductId, name, cost, ingredients);
        productCatalogue.put(nextProductId, pastry);
        nextProductId++;
    }

    public Pastry getPastryByName(String name) {
        return productCatalogue.values().stream()
                .filter(product -> product instanceof Pastry)
                .map(product -> (Pastry) product)
                .filter(pastry -> pastry.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
