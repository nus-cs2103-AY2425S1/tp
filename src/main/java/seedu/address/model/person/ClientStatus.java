package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class ClientStatus {
    
    public static final String MESSAGE_CONSTRAINTS =
            "Client status must be either “active”, “unresponsive”, “potential”, or “old”(case sensitive).";

    private final String value;

    /**
     * Constructs a {@code ClientStatus}.
     *
     * @param status A valid client status.
     */
    public ClientStatus(String status) {
        requireNonNull(status);
        checkArgument(isValidClientStatus(status), MESSAGE_CONSTRAINTS);
        value = status;
    }

    /**
     * Returns true if the given string is a valid client status.
     */
    public static boolean isValidClientStatus(String test) {
        return test.equals("active") || test.equals("unresponsive")
                || test.equals("potential") || test.equals("old");
    }

    @Override
    public String toString() {
        return value.toUpperCase();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClientStatus)) {
            return false;
        }

        ClientStatus otherStatus = (ClientStatus) other;
        return value == otherStatus.value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
