package seedu.address.model.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person added in the meet-up list.
 * Guarantees: immutable; Person exists and is found in buyerList.
 */
public class AddedBuyer {

    public static final String MESSAGE_CONSTRAINTS =
            "Name of buyer added should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String addedBuyerName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param addedBuyerName A valid added buyer name.
     */
    public AddedBuyer(String addedBuyerName) {
        requireNonNull(addedBuyerName);
        checkArgument(isValidBuyerName(addedBuyerName), MESSAGE_CONSTRAINTS);
        this.addedBuyerName = addedBuyerName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidBuyerName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddedBuyer)) {
            return false;
        }

        AddedBuyer otherAddedBuyer = (AddedBuyer) other;
        return addedBuyerName.equals(otherAddedBuyer.addedBuyerName);
    }

    @Override
    public int hashCode() {
        return addedBuyerName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + addedBuyerName + ']';
    }

}
