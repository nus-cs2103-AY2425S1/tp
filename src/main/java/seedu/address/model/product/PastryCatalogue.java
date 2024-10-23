package seedu.address.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Catalogue for managing pastries.
 */
public class PastryCatalogue extends Catalogue {

    public PastryCatalogue() {
        addDefaultProducts();
    }

    @Override
    public void addDefaultProducts() {
        // add 3 default pastries
        addPastry("Strawberry Waffle", 5.50, new ArrayList<>(List.of(
                IngredientCatalogue.STRAWBERRY,
                IngredientCatalogue.FLOUR,
                IngredientCatalogue.SUGAR
        )));

        addPastry("Chocolate Donut", 4.00, new ArrayList<>(List.of(
                IngredientCatalogue.CHOCOLATE,
                IngredientCatalogue.FLOUR,
                IngredientCatalogue.SUGAR
        )));

        addPastry("Cheesecake", 6.50, new ArrayList<>(List.of(
                IngredientCatalogue.CHEESE,
                IngredientCatalogue.CREAM,
                IngredientCatalogue.SUGAR
        )));
    }

    public int getNextProductId() {
        return nextProductId;
    }

    public void addPastry(String name, double cost, ArrayList<Ingredient> ingredients) {
        Pastry pastry = new Pastry(nextProductId, name, cost, ingredients);
        productCatalogue.put(nextProductId, pastry);  // Store in the product catalogue
        nextProductId++;  // Increment the product ID for the next pastry
    }

    /**
     * Retrieves a pastry from the catalogue by its name.
     * @param name The name of the pastry to retrieve.
     * @return The pastry if found, otherwise null.
     */
    public Pastry getPastryByName(String name) {
        Optional<Pastry> foundPastry = productCatalogue.values().stream()
                .filter(product -> product instanceof Pastry)
                .map(product -> (Pastry) product)
                .filter(pastry -> pastry.getName().equalsIgnoreCase(name))
                .findFirst();
        return foundPastry.orElse(null);
    }
}