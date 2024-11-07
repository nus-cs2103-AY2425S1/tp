package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Pastry;

class JsonAdaptedPastry {
    private final int productId;
    private final String name;
    private final double cost;
    private final List<JsonAdaptedIngredient> ingredients;

    @JsonCreator
    public JsonAdaptedPastry(@JsonProperty("productId") int productId,
                             @JsonProperty("name") String name,
                             @JsonProperty("cost") double cost,
                             @JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients) {
        this.productId = productId;
        this.name = name;
        this.cost = cost;
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
    }

    public JsonAdaptedPastry(Pastry source) {
        productId = source.getProductId();
        name = source.getName();
        cost = source.getCost();
        ingredients = source.getIngredients().stream()
                .map(JsonAdaptedIngredient::new)
                .collect(Collectors.toList());
    }

    public Pastry toModelType() throws IllegalValueException {
        ArrayList<Ingredient> modelIngredients = new ArrayList<>();
        for (JsonAdaptedIngredient ingredient : ingredients) {
            modelIngredients.add(ingredient.toModelType());
        }
        return new Pastry(productId, name, cost, modelIngredients);
    }
}