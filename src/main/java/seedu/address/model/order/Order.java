package seedu.address.model.order;

/**
 * Order represent an order ordered by a customer, each order is uniquely identified as its order name
 */
public class Order {
    public static final String MESSAGE_CONSTRAINTS =
            "Order name must only contain \n"
                    + "1. Alphanumeric characters or \n"
                    + "2. spaces (not as the first character)";

    /**
     * Order name should contain alphanumeric character only
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9][a-zA-Z0-9\\s]*$";

    private final String name;

    /**
     * Create a new order
     * @param name of order
     */
    public Order(String name) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.name = name.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid order name.
     */
    public static boolean isValidName(String name) {
        return name.matches(VALIDATION_REGEX) && !name.isEmpty();
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
