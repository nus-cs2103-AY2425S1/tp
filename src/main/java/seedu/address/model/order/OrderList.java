package seedu.address.model.order;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage a list of orders, including both supply and customer orders.
 */
public class OrderList {
    private List<SupplyOrder> supplyOrders;
    private List<CustomerOrder> customerOrders;

    /**
     * Constructor for OrderList.
     * Initializes the lists of supply and customer orders.
     */
    public OrderList() {
        this.supplyOrders = new ArrayList<>();
        this.customerOrders = new ArrayList<>();
    }

    /**
     * Adds a supply order to the list.
     *
     * @param supplyOrder The supply order to be added.
     */
    public void addSupplyOrder(SupplyOrder supplyOrder) {
        supplyOrders.add(supplyOrder);
    }

    /**
     * Adds a customer order to the list.
     *
     * @param customerOrder The customer order to be added.
     */
    public void addCustomerOrder(CustomerOrder customerOrder) {
        customerOrders.add(customerOrder);
    }

    /**
     * Retrieves all supply orders.
     *
     * @return A list of all supply orders.
     */
    public List<SupplyOrder> getSupplyOrders() {
        return new ArrayList<>(supplyOrders);  // Return a copy of the list to avoid modification
    }

    /**
     * Retrieves all customer orders.
     *
     * @return A list of all customer orders.
     */
    public List<CustomerOrder> getCustomerOrders() {
        return new ArrayList<>(customerOrders);  // Return a copy of the list to avoid modification
    }

    /**
     * Finds a supply order by the customer's phone number.
     *
     * @param phoneNumber The phone number of the customer.
     * @return The supply order associated with the given phone number, or null if not found.
     */
    public SupplyOrder findSupplyOrderByPhoneNumber(String phoneNumber) {
        for (SupplyOrder order : supplyOrders) {
            if (order.getPhoneNumber().equals(phoneNumber)) {
                return order;
            }
        }
        return null;  // Return null if not found
    }

    /**
     * Finds a customer order by the customer's phone number.
     *
     * @param phoneNumber The phone number of the customer.
     * @return The customer order associated with the given phone number, or null if not found.
     */
    public CustomerOrder findCustomerOrderByPhoneNumber(String phoneNumber) {
        for (CustomerOrder order : customerOrders) {
            if (order.getPhoneNumber().equals(phoneNumber)) {
                return order;
            }
        }
        return null;  // Return null if not found
    }

    /**
     * Removes a supply order by the customer's phone number.
     *
     * @param phoneNumber The phone number of the customer.
     * @return True if the order was successfully removed, false otherwise.
     */
    public boolean removeSupplyOrder(String phoneNumber) {
        return supplyOrders.removeIf(order -> order.getPhoneNumber().equals(phoneNumber));
    }

    /**
     * Removes a customer order by the customer's phone number.
     *
     * @param phoneNumber The phone number of the customer.
     * @return True if the order was successfully removed, false otherwise.
     */
    public boolean removeCustomerOrder(String phoneNumber) {
        return customerOrders.removeIf(order -> order.getPhoneNumber().equals(phoneNumber));
    }

    @Override
    public String toString() {
        return "OrderList{" +
                "supplyOrders=" + supplyOrders +
                ", customerOrders=" + customerOrders +
                '}';
    }
}
