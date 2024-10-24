package seedu.address.model.person;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's interest in the address book.
 */
public class Interest {

    public static final String MESSAGE_CONSTRAINTS =
            "Interest should not be blank and should only contain alphanumeric characters and spaces.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String interestName;

    /**
     * Constructs a {@code Interest}.
     *
     * @param interest A valid interest.
     */
    public Interest(String interest) {
        requireNonNull(interest);
        checkArgument(isValidInterest(interest), MESSAGE_CONSTRAINTS);
        interestName = interest;
    }
    /**
     * Returns true if a given string is a valid interest entry.
     */
    public static boolean isValidInterest(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return interestName;
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
        return interestName.equals(otherInterest.interestName);
    }

    @Override
    public int hashCode() {
        return interestName.hashCode();
    }
}
