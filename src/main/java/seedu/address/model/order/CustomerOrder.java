package seedu.address.model.order;

import java.util.Date;
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
