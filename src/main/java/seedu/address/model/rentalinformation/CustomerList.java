package seedu.address.model.rentalinformation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;

import seedu.address.commons.util.RentalUtil;

public class CustomerList {
    public static final String MESSAGE_CONSTRAINTS =
            "Customer list should separate the names by ; if there is more than one customer";
    public static final String VALIDATION_REGEX = "^(?!;)([^;]+(;[^;]+)*)?$";

    private ArrayList<String> customerList;

    public CustomerList(String customerList) {
        requireNonNull(customerList);
        checkArgument(isValidCustomerList(customerList), MESSAGE_CONSTRAINTS);
        this.customerList = RentalUtil.convertStringToCustomerArrayList(customerList);
    }

    public static boolean isValidCustomerList(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
