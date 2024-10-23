package seedu.address.model.order;

import seedu.address.model.order.OrderStatus;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Product;

import java.util.List;

public class SupplyOrder extends Order {
    public SupplyOrder(String phoneNumber, List<Product> items, OrderStatus status) {
        super(phoneNumber, items, status);
    }

    @Override
    public String getOrderType() {
        return "Supply Order";
    }
}
