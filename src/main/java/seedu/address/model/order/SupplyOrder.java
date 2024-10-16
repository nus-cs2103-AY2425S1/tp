package seedu.address.model.order;

import seedu.address.model.product.Ingredient;
import java.util.List;

public class SupplyOrder extends Order {
    public SupplyOrder(String phoneNumber, List<Ingredient> items, String status) {
        super(phoneNumber, items, status);
    }

    @Override
    public String getOrderType() {
        return "Supply Order";
    }
}
