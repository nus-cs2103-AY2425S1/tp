package seedu.address.model.order;

import java.util.Date;
import java.util.List;

public class SupplyOrder extends Order {
    public SupplyOrder(String orderId, Date orderDate, List<String> items, String status, double totalAmount) {
        super(orderId, orderDate, items, status, totalAmount);
    }

    @Override
    public String getOrderType() {
        return "Supply Order";
    }
}
