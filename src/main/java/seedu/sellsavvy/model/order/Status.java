package seedu.sellsavvy.model.order;

import seedu.sellsavvy.commons.exceptions.IllegalValueException;

public enum Status {
    PENDING  {
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

    public static String MESSAGE_CONSTRAINTS = "Status should be \"Pending\" or \"Completed\"";

    public static Status fromString(String status) throws IllegalValueException {
        try {
            return Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }
    }
}
