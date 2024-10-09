package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Nickname in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidNickname(String)}
 */
public class Nickname {

    public static final String MESSAGE_CONSTRAINTS = "Nicknames should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String value;

    /**
     * Constructs a {@code Nickname}.
     *
     * @param nickname A valid nickname.
     */
    public Nickname(String nickname) {
        requireNonNull(nickname);
        //checkArgument(isValidNickname(nickname), MESSAGE_CONSTRAINTS);
        this.value = nickname;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidNickname(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Nickname)) {
            return false;
        }

        Nickname otherNickname = (Nickname) other;
        return value.equals(otherNickname.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return value;
    }

}
