package seedu.address.model.product;

import java.util.ArrayList;

public class Pastry extends Product {
    private final ArrayList<Ingredient> ingredients;

    public Pastry(int productId, String name, double cost, ArrayList<Ingredient> ingredients) {
        super(productId, name, cost);
        this.ingredients = new ArrayList<>(ingredients);  // Defensive copy
    }

    public ArrayList<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    @Override
    public String toString() {
        return super.toString() + ", Ingredients: " + ingredients;
    }
}
