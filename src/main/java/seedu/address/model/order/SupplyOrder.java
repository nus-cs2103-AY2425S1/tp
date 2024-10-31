package seedu.address.model.order;

import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

import java.util.List;

public class SupplyOrder extends Order {
    public SupplyOrder(Person person, List<Product> items, OrderStatus status) {
        super(person, items, status);
    }

    @Override
    public String getOrderType() {
        return "Supplier Order";
    }
}
