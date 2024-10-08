package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents a Person's owed tuition fee in the address book.
 */
public class Owed extends Fee {
    public static final String MESSAGE_CONSTRAINTS = "Owed " + Fee.MESSAGE_CONSTRAINTS;

    /**
     * Constructs a {@code Owed}.
     *
     * @param owed A valid owed.
     */
    public Owed(String owed) {
        super(owed);
    }

    public static boolean isValidOwed(String test) {
        return Fee.isValidFee(test);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Owed)) {
            return false;
        }

        Owed otherOwed = (Owed) other;
        return value == otherOwed.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, Owed.class);
    }
}
