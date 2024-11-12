package seedu.address.model.person;
import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.Group;

/**
 * Represents a Person's comment in the address book.
 * Guarantees: immutable; is always valid
 */
public class Comment {
    private static final Logger logger = LogsCenter.getLogger(Group.class);

    public final String value;

    /**
     * Constructs a {@code Comment} object with the specified comment value.
     *
     * <p>The constructor ensures that the provided comment is not {@code null} and logs
     * a message when a new comment is added.</p>
     *
     * @param comment The comment string to be assigned to the {@code Comment} object. Cannot be {@code null}.
     * @throws NullPointerException If the provided comment is {@code null}.
     */
    public Comment(String comment) {
        requireNonNull(comment);
        value = comment;
        logger.info("A comment had been added: " + this.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        // AI was used to write this equals method
        return other == this || (other instanceof Comment && value.equals(((Comment) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
