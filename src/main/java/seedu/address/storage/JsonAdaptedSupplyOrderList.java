package seedu.address.storage;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.SupplyOrderList;

class JsonAdaptedSupplyOrderList {
    private final List<JsonAdaptedSupplyOrder> supplyOrders;

    @JsonCreator
    public JsonAdaptedSupplyOrderList(@JsonProperty("supplyOrders") List<JsonAdaptedSupplyOrder> supplyOrders) {
        this.supplyOrders = supplyOrders != null ? supplyOrders : List.of();
    }

    public JsonAdaptedSupplyOrderList(SupplyOrderList source) {
        supplyOrders = source.getOrders().stream()
                .map(JsonAdaptedSupplyOrder::new)
                .collect(Collectors.toList());
    }

    public SupplyOrderList toModelType() throws IllegalValueException {
        SupplyOrderList supplyOrderList = new SupplyOrderList();
        for (JsonAdaptedSupplyOrder jsonAdaptedSupplyOrder : supplyOrders) {
            supplyOrderList.addOrder(jsonAdaptedSupplyOrder.toModelType());
        }
        return supplyOrderList;
    }
}