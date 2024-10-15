package seedu.address.model.order;

import java.util.HashMap;

/**
 * Order represent an order ordered by a customer, each order is uniquely identified as its order name
 */
public class Order {
    /**
     * Hashmap that map name of order to a single unique order
     */
    private static final HashMap<String, Order> nameToOrder = new HashMap<>();

    /**
     * Name of order
     */
    private final String name;

    private Order(String name) {
        this.name = name;
    }

    /**
     * Factory method for Order to prevent creating multiple instances for a single order,
     * when writing test case involving Order, you should call Order.removeAllOrder() first
     * @return A new order specified by a order name
     */
    public static Order of(String name) {
        Order order = nameToOrder.get(name);
        if (order == null) {
            order = new Order(name);
            nameToOrder.put(name, order);
        }
        return order;
    }

    /**
     * Remove an order based on its name
     * @param name Name of order
     * @return True if removed successfully
     */
    public static boolean removeOrder(String name) {
        if (nameToOrder.get(name) != null) {
            nameToOrder.remove(name);
            return true;
        }
        return false;
    }

    /**
     * Remove all order stored
     */
    public static void removeAllOrder() {
        nameToOrder.clear();
    }

    /**
     * Check if a order has already been created
     * @param name Name of the order to check
     * @return True if the order has already been created
     */
    public static boolean orderExist(String name) {
        return nameToOrder.get(name) != null;
    }

    public static int getTotalOrderCount() {
        return nameToOrder.size();
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

        return rhs == this;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
