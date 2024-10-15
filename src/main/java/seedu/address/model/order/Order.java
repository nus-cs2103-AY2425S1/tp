package seedu.address.model.order;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Order represent an order ordered by a customer, each order is uniquely identified as its order name
 */
public class Order {
    private final String name;

    public Order(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object rhs) {
        if (!(rhs instanceof Order)) {
            return false;
        }

        return this.name.equals(((Order) rhs).name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
