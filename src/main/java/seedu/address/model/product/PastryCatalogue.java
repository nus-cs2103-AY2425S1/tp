package seedu.address.model.product;

import seedu.address.model.util.SampleDataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Catalogue for managing pastries. Provides methods to add pastries with specified ingredients
 * and retrieve pastries by name. Initializes with a set of default pastries.
 */
public class PastryCatalogue extends Catalogue {

    /**
     * Initializes the pastry catalogue with default pastries.
     */
    public PastryCatalogue() {
        // Populate catalogue with default pastries from SampleDataUtil
        List<Pastry> defaultPastries = SampleDataUtil.getDefaultPastries();
        for (Pastry pastry : defaultPastries) {
            addPastry(pastry.getName(), pastry.getCost(), pastry.getIngredients());
        }
    }

    /**
     * Adds a pastry to the catalogue with a specified name, cost, and list of ingredients.
     *
     * @param name        The name of the pastry.
     * @param cost        The cost of the pastry.
     * @param ingredients The list of ingredients required to make the pastry.
     */
    public void addPastry(String name, double cost, ArrayList<Ingredient> ingredients) {
        Pastry pastry = new Pastry(nextProductId, name, cost, ingredients);
        productCatalogue.put(nextProductId, pastry);
        nextProductId++;
    }

    /**
     * Retrieves a pastry from the catalogue by its name.
     *
     * @param name The name of the pastry to retrieve.
     * @return The pastry if found, otherwise {@code null}.
     */
    public Pastry getPastryByName(String name) {
        return productCatalogue.values().stream()
                .filter(product -> product instanceof Pastry)
                .map(product -> (Pastry) product)
                .filter(pastry -> pastry.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}