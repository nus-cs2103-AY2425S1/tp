package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Nickname in the address book.
 * Guarantees: immutable}
 */
public class Nickname {

    public static final String MESSAGE_CONSTRAINTS = "Nicknames should not be empty";
    public final String value;

    /**
     * Constructs a {@code Nickname}.
     *
     * @param nickname A valid nickname.
     */
    public Nickname(String nickname) {
        requireNonNull(nickname);
        this.value = nickname.trim(); // in ParserUtil.java also have trim()
    }

    /**
     * Returns true if a given nickname is empty.
     */
    public boolean isEmpty() {
        return value.isEmpty();
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

    /**
     * Compares the nickname of another person contact in alphabetical order.
     *
     * @param otherNickname Nickname of another person contact
     * @return Integer status of the comparison
     */
    public int compareTo(Nickname otherNickname) {
        return value.toLowerCase().compareTo(otherNickname.value.toLowerCase());
    }
}
