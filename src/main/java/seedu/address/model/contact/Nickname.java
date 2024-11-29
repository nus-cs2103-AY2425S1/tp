package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Nickname in the address book.
 * Guarantees: immutable
 */
public class Nickname {

    public final String value;

    /**
     * Constructs a {@code Nickname}.
     *
     * @param nickname A valid nickname.
     */
    public Nickname(String nickname) {
        requireNonNull(nickname);
        this.value = nickname.trim();
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
     * Formats state as text for viewing.
     */
    public String toString() {
        return value;
    }

    /**
     * Compares the nickname of another contact in alphabetical order.
     *
     * @param otherNickname Nickname of another contact
     * @return Integer status of the comparison
     */
    public int compareTo(Nickname otherNickname) {
        return value.toLowerCase().compareTo(otherNickname.value.toLowerCase());
    }
}
