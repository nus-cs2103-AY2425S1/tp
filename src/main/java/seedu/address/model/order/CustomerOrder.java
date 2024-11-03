package seedu.address.model.order;

import java.util.List;

import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * Class representing a customer order.
 */
public class CustomerOrder extends Order {
    public CustomerOrder(Person person, List<Product> items, OrderStatus status) {
        super(person, items, status);
    }

    @Override
    public String getOrderType() {
        return "Customer Order";
    }
}
