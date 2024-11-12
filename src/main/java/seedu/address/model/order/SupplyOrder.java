package seedu.address.model.order;

import java.util.List;

import seedu.address.model.person.Person;
import seedu.address.model.product.Product;
import seedu.address.model.util.Remark;

/**
 * Class representing a customer order.
 */
public class SupplyOrder extends Order {
    public SupplyOrder(Person person, List<Product> items, OrderStatus status, Remark remark) {
        super(person, items, status, remark);
    }

    @Override
    public String getOrderType() {
        return "Supply Order";
    }
}
