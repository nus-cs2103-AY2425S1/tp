package seedu.address.storage;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.CustomerOrderList;

class JsonAdaptedCustomerOrderList {
    private final List<JsonAdaptedCustomerOrder> customerOrders;

    @JsonCreator
    public JsonAdaptedCustomerOrderList(@JsonProperty("customerOrders") List<JsonAdaptedCustomerOrder> customerOrders) {
        this.customerOrders = customerOrders != null ? customerOrders : List.of();
    }

    public JsonAdaptedCustomerOrderList(CustomerOrderList source) {
        customerOrders = source.getOrders().stream()
                .map(JsonAdaptedCustomerOrder::new)
                .collect(Collectors.toList());
    }

    public CustomerOrderList toModelType() throws IllegalValueException {
        CustomerOrderList customerOrderList = new CustomerOrderList();
        for (JsonAdaptedCustomerOrder jsonAdaptedCustomerOrder : customerOrders) {
            customerOrderList.addOrder(jsonAdaptedCustomerOrder.toModelType());
        }
        return customerOrderList;
    }
}