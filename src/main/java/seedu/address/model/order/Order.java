package seedu.address.model.order;

/**
 * Order represent an order ordered by a customer, each order is uniquely identified as its order name
 */
public class Order {
    private final String name;

    public Order(String name) {
        this.name = name.toLowerCase();
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

        Order other = (Order) rhs;
        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
