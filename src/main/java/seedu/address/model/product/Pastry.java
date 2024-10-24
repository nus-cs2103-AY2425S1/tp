package seedu.address.model.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Pastry extends Product {
    private final ArrayList<Ingredient> ingredients;

    public Pastry(int productId, String name, double cost, ArrayList<Ingredient> ingredients) {
        super(productId, name, cost);
        this.ingredients = new ArrayList<>(ingredients);  // Defensive copy
        Collections.sort(ingredients, Comparator.comparingInt(Ingredient::getProductId));
    }

    public ArrayList<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    @Override
    public String toString() {
        StringBuilder ingredientsString = new StringBuilder();
        Collections.sort(ingredients, Comparator.comparingInt(Ingredient::getProductId));
        for (Ingredient ingredient : ingredients) {
            ingredientsString.append("          ");
            ingredientsString.append(ingredient.toShortString()).append("\n");
        }
        return super.toString() + "\n     Ingredients: \n" + ingredientsString;
    }
}
