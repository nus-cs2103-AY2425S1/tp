package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.SupplyOrder;
import seedu.address.model.person.Person;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.Product;

import java.util.List;
import java.util.stream.Collectors;

class JsonAdaptedSupplyOrder {
    private final JsonAdaptedPerson person;
    private final List<JsonAdaptedIngredient> ingredients;
    private final String status;

    @JsonCreator
    public JsonAdaptedSupplyOrder(@JsonProperty("person") JsonAdaptedPerson person,
                                  @JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients,
                                  @JsonProperty("status") String status) {
        this.person = person;
        this.ingredients = ingredients != null ? ingredients : List.of();
        this.status = status;
    }

    public JsonAdaptedSupplyOrder(SupplyOrder source) {
        person = new JsonAdaptedPerson(source.getPerson());
        ingredients = source.getItems().stream()
                .map(item -> (Ingredient) item)
                .map(JsonAdaptedIngredient::new)
                .collect(Collectors.toList());
        status = source.getStatus().toString();
    }

    public SupplyOrder toModelType() throws IllegalValueException {
        List<Product> modelIngredients = ingredients.stream()
                .map(ingredient -> {
                    try {
                        return ingredient.toModelType();
                    } catch (IllegalValueException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        Person modelPerson = person.toModelType();
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        return new SupplyOrder(modelPerson, modelIngredients, orderStatus);
    }
}