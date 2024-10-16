package seedu.address.model.order;

import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Product;

import java.util.List;

public class SupplyOrder extends Order {
    public SupplyOrder(String phoneNumber, List<Product> items, String status) {
        super(phoneNumber, items, status);
    }

    @Override
    public String getOrderType() {
        return "Supply Order";
    }
}
