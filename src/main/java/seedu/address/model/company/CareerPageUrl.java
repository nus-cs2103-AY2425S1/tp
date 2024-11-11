package seedu.address.model.company;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Company's career page in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidAddress(String)}
 */
public class CareerPageUrl {

    public static final String MESSAGE_CONSTRAINTS = "Career page URLs can take any values, and it should not be blank";

    /*
     * The first character of the url must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code CareerPageURL}.
     *
     * @param url A valid address.
     */
    public CareerPageUrl(String url) {
        requireNonNull(url);
        checkArgument(isValidAddress(url), MESSAGE_CONSTRAINTS);
        value = url;
    }

    /**
     * Returns true if a given string is a valid url.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
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

        // instanceof handles nulls
        if (!(other instanceof CareerPageUrl)) {
            return false;
        }

        CareerPageUrl otherUrl = (CareerPageUrl) other;
        return value.equals(otherUrl.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
