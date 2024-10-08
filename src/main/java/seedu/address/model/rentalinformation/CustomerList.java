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
    public static final String VALIDATION_REGEX = "^(?!;)([^;]+(;[^;]+)*)?$";

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
     * Validates a customer list string against a predefined regex pattern.
     *
     * @param test The string to be validated as a customer list.
     * @return {@code true} if the string matches the validation regex;
     *         {@code false} otherwise.
     */
    public static boolean isValidCustomerList(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
