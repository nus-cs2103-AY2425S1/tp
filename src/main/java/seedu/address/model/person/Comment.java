package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's comment the user made in the address book.
 */
public class Comment {

    public static final String MESSAGE_CONSTRAINTS =
            "comments can be any input including special characters or only spaces";
    public static final String VALIDATION_REGEX = ".*";
    public final String fullComment;

    /**
     * Constructs a {@code Comment}.
     *
     * @param comment A valid comment.
     */
    public Comment(String comment) {
        requireNonNull(comment);
        checkArgument(isValidComment(comment), MESSAGE_CONSTRAINTS);
        fullComment = comment;
    }

    /**
     * Returns true if a given string is a valid comment.
     */
    public static boolean isValidComment(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullComment;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Comment)) {
            return false;
        }

        Comment otherComment = (Comment) other;
        return otherComment.fullComment.equals(fullComment);
    }

    @Override
    public int hashCode() {
        return fullComment.hashCode();
    }

}
