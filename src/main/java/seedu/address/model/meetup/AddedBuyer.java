package seedu.address.model.meetup;

import seedu.address.model.buyer.Name;

/**
 * Represents the name of the buyer added in the meet-up list.
 * Guarantees: immutable;
 */
public class AddedBuyer extends Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Name of buyer added should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * Constructs a {@code AddedBuyer}.
     *
     * @param fullName A valid added buyer name.
     */
    public AddedBuyer(String fullName) {
        super(fullName);
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
        return super.fullName.equals(otherAddedBuyer.fullName);
    }

    @Override
    public int hashCode() {
        return super.fullName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return '[' + super.fullName + ']';
    }

}
