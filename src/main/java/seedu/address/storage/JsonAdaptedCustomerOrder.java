package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.person.Person;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.Product;
import seedu.address.model.util.Remark;

import java.util.List;
import java.util.stream.Collectors;

class JsonAdaptedCustomerOrder {
    private final JsonAdaptedPerson person;
    private final List<JsonAdaptedPastry> pastrys;
    private final String status;
    private final String remark;

    @JsonCreator
    public JsonAdaptedCustomerOrder(@JsonProperty("person") JsonAdaptedPerson person,
                                    @JsonProperty("pastrys") List<JsonAdaptedPastry> pastrys,
                                    @JsonProperty("status") String status,
                                    @JsonProperty("remark") String remark) {
        this.person = person;
        this.pastrys = pastrys != null ? pastrys : List.of();
        this.status = status;
        this.remark = remark;
    }

    public JsonAdaptedCustomerOrder(CustomerOrder source) {
        person = new JsonAdaptedPerson(source.getPerson());
        pastrys = source.getItems().stream()
                .map(item -> (Pastry) item)
                .map(JsonAdaptedPastry::new)
                .collect(Collectors.toList());
        status = source.getStatus().toString().toUpperCase();
        remark = source.getRemark().toString();
    }

    public CustomerOrder toModelType() throws IllegalValueException {
        List<Product> modelPastrys = pastrys.stream()
                .map(pastry -> {
                    try {
                        return pastry.toModelType();
                    } catch (IllegalValueException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        Person modelPerson = person.toModelType();
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        return new CustomerOrder(modelPerson, modelPastrys, orderStatus, new Remark(remark));
    }
}