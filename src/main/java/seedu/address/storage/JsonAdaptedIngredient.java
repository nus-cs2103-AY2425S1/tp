package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.product.Ingredient;

class JsonAdaptedIngredient {
    private final int productId;
    private final String name;
    private final double cost;

    @JsonCreator
    public JsonAdaptedIngredient(@JsonProperty("productId") int productId,
                                 @JsonProperty("name") String name,
                                 @JsonProperty("cost") double cost) {
        this.productId = productId;
        this.name = name;
        this.cost = cost;
    }

    public JsonAdaptedIngredient(Ingredient source) {
        productId = source.getProductId();
        name = source.getName();
        cost = source.getCost();
    }

    public Ingredient toModelType() throws IllegalValueException {
        return new Ingredient(productId, name, cost);
    }
}