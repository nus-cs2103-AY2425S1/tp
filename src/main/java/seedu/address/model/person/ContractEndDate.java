package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a Person's Contract End Date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class ContractEndDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Contract End Date should only contain numeric characters and dashes in the format 'YYYY-MM-DD', and it"
                    + " should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public final LocalDate contractEndDate;
    public final String value;

    /**
     * Constructs a {@code ContractEndDate}.
     *
     * @param date A valid date.
     */
    public ContractEndDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.contractEndDate = convertStringToDate(date);
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static LocalDate convertStringToDate(String date) {
        return LocalDate.parse(date);
    }

    @Override
    public String toString() {
        return contractEndDate.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContractEndDate)) {
            return false;
        }

        ContractEndDate otherContractEndDate = (ContractEndDate) other;
        return contractEndDate.equals(otherContractEndDate.contractEndDate);
    }

    @Override
    public int hashCode() {
        return contractEndDate.hashCode();
    }

}
