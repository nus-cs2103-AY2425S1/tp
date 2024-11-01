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
     * Constructs an {@code Comment}.
     *
     * @param comment A valid comment.
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
        return other == this // short circuit if same object
                || (other instanceof Comment // instanceof handles nulls
                && value.equals(((Comment) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
