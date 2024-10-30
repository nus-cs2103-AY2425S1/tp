package seedu.sellsavvy.model.order;

import seedu.sellsavvy.commons.exceptions.IllegalValueException;

/**
 * Represents the status of a Person's Order in the address book.
 * An order is either PENDING or COMPLETED.
 */
public enum Status {
    PENDING {
        @Override
        public String toString() {
            return "Pending";
        }
    },
    COMPLETED {
        @Override
        public String toString() {
            return "Completed";
        }
    };

    public static final String MESSAGE_CONSTRAINTS = "Status should be \"Pending\" or \"Completed\"";

    /**
     * Returns the enum name of the given string.
     */
    public static Status fromString(String status) throws IllegalValueException {
        try {
            return Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns value of the status as a string.
     */
    public String getValue() {
        return this.toString().toLowerCase();
    }
}
