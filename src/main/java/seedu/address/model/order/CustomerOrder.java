package seedu.address.model.order;

import java.util.Date;
import java.util.List;

public class CustomerOrder extends Order {
    public CustomerOrder(String phoneNumber, Date orderDate, List<Pastries> items, String status) {
        super(phoneNumber, orderDate, items, status);
    }

    @Override
    public String getOrderType() {
        return "Customer Order";
    }
}
