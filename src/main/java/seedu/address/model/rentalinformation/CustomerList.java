package seedu.address.model.rentalinformation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;

import seedu.address.commons.util.RentalUtil;

/**
 * Represents a Rental Information's customer list in the address book.
 * Guarantees: is valid as declared in {@link #isValidCustomerList(String)}
 */
public class CustomerList {
    public static final String MESSAGE_CONSTRAINTS =
            "Customer list should separate the names by ; if there is more than one customer";
    public static final String VALIDATION_REGEX = "^(?!;)(?!\\s*$)([^;]+(;[^;]+)*)?$";

    public final ArrayList<String> customerList;

    /**
     * Constructs a {@code CustomerList}.
     *
     * @param customerList A valid list of customer name(s).
     */
    public CustomerList(String customerList) {
        requireNonNull(customerList);
        checkArgument(isValidCustomerList(customerList), MESSAGE_CONSTRAINTS);
        this.customerList = RentalUtil.convertStringToCustomerArrayList(customerList);
    }

    /**
     * Constructs an {@code CustomerList} with customerList as null (not provided).
     */
    public CustomerList() {
        customerList = null;
    }

    /**
     * Validates a customer list string against a predefined regex pattern.
     *
     * @param test The string to be validated as a customer list.
     * @return {@code true} if the string matches the validation regex;
     *         {@code false} otherwise.
     */
    public static boolean isValidCustomerList(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public ArrayList<String> getCustomerList() {
        return customerList;
    }

    @Override
    public String toString() {
        if (customerList == null) {
            return "null";
        }

        StringBuilder allNames = new StringBuilder();
        for (int i = 0; i < customerList.size(); i++) {
            if (i < customerList.size() - 1) {
                allNames.append(customerList.get(i)).append(";");
            } else {
                allNames.append(customerList.get(i));
            }
        }

        return allNames.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CustomerList)) {
            return false;
        }

        CustomerList otherCustomerList = (CustomerList) other;
        if (this.customerList == null && otherCustomerList.customerList == null) {
            return true;
        } else if (this.customerList == null || otherCustomerList.customerList == null) {
            return false;
        }

        return RentalUtil.isCustomerListSame(this, otherCustomerList);
    }

    @Override
    public int hashCode() {
        return customerList.hashCode();
    }
}
