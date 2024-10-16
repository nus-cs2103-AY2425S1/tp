package seedu.address.model.order;

import java.util.Date;
import java.util.List;

public class SupplyOrder extends Order {
    public SupplyOrder(String phoneNumber, Date orderDate, List<Ingredients> items, String status) {
        super(phoneNumber, orderDate, items, status);
    }

    @Override
    public String getOrderType() {
        return "Supply Order";
    }
}
