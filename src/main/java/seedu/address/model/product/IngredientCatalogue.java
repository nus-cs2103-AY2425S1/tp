package seedu.address.model.product;

public class IngredientCatalogue extends Catalogue {
    // Static references to default ingredients for direct access
    public static final Ingredient FLOUR = new Ingredient(1, "Flour", 1.50);
    public static final Ingredient SUGAR = new Ingredient(2, "Sugar", 0.80);
    public static final Ingredient STRAWBERRY = new Ingredient(3, "Strawberry", 3.00);
    public static final Ingredient CHOCOLATE = new Ingredient(4, "Chocolate", 2.50);
    public static final Ingredient CHEESE = new Ingredient(5, "Cheese", 4.00);
    public static final Ingredient CREAM = new Ingredient(6, "Cream", 2.00);

    public IngredientCatalogue() {
        addDefaultProducts();
        super.nextProductId += 6;
    }

    @Override
    public void addDefaultProducts() {
        // add 6 default ingredients
        addIngredient(FLOUR);
        addIngredient(SUGAR);
        addIngredient(STRAWBERRY);
        addIngredient(CHOCOLATE);
        addIngredient(CHEESE);
        addIngredient(CREAM);
    }

    private void addIngredient(Ingredient ingredient) {
        productCatalogue.put(ingredient.getProductId(), ingredient);
        nextProductId++;
    }
}
