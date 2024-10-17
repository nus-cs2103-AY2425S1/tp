package seedu.address.model.person;


/**
 * Represents a Person's interest in the address book.
 */
public class Interest {

    public static final String MESSAGE_CONSTRAINTS =
            "Interest should not be blank and should only contain alphanumeric characters and spaces.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String value;

    /**
     * Constructs a {@code Interest}.
     *
     * @param interest A valid interest.
     */
    public Interest(String interest) {
        value = interest;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Interest)) {
            return false;
        }

        Interest otherInterest = (Interest) other;
        return value.equals(otherInterest.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
