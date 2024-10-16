package seedu.address.model.product;

import java.util.ArrayList;
import java.util.List;

/**
 * Catalogue for managing pastries.
 */
public class PastryCatalogue extends Catalogue {

    public PastryCatalogue() {
        addDefaultProducts();
        super.nextProductId += 3;
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
}
