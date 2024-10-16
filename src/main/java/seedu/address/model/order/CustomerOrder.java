package seedu.address.model.order;

import seedu.address.model.product.Pastry;
import java.util.List;

public class CustomerOrder extends Order {
    public CustomerOrder(String phoneNumber, List<Pastry> items, String status) {
        super(phoneNumber, items, status);
    }

    @Override
    public String getOrderType() {
        return "Customer Order";
    }
}
