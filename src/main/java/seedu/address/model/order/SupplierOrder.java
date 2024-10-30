package seedu.address.model.order;

import seedu.address.model.product.Product;

import java.util.List;

public class SupplierOrder extends Order {
    public SupplierOrder(String phoneNumber, List<Product> items, OrderStatus status) {
        super(phoneNumber, items, status);
    }

    @Override
    public String getOrderType() {
        return "Supply Order";
    }
}
