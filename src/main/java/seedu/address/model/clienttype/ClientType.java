package seedu.address.model.clienttype;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a ClientType in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidClientTypeName(String)}
 */
public class ClientType {

    public static final String MESSAGE_CONSTRAINTS = "ClientTypes names should be alphanumeric";
    public static final String VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String clientTypeName;

    /**
     * Constructs a {@code ClientType}.
     *
     * @param clientTypeName A valid tag name.
     */
    public ClientType(String clientTypeName) {
        requireNonNull(clientTypeName);
        checkArgument(isValidClientTypeName(clientTypeName), MESSAGE_CONSTRAINTS);
        this.clientTypeName = clientTypeName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidClientTypeName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClientType)) {
            return false;
        }

        ClientType otherClientType = (ClientType) other;
        return clientTypeName.equals(otherClientType.clientTypeName);
    }

    @Override
    public int hashCode() {
        return clientTypeName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + clientTypeName + ']';
    }

}
