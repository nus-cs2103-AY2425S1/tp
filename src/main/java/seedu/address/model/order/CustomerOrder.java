package seedu.address.model.order;

import seedu.address.model.order.OrderStatus;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.Product;

import java.util.List;

public class CustomerOrder extends Order {
    public CustomerOrder(String phoneNumber, List<Product> items, OrderStatus status) {
        super(phoneNumber, items, status);
    }

    @Override
    public String getOrderType() {
        return "Customer Order";
    }
}
