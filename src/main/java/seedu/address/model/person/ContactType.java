package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class ContactType {

    public static final String MESSAGE_CONSTRAINTS = "ContactType must either be \"Work\" or \"Personal\", " +
            "or it can be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final ContactTypeEnum value;
    public ContactType(String contactType) {
        requireNonNull(contactType);
        checkArgument(isValidContactType(contactType), MESSAGE_CONSTRAINTS);
        switch(contactType) {
        case ("WORK"):
            value = ContactTypeEnum.WORK;
            break;
        case("PERSONAL"):
        default:
            value = ContactTypeEnum.PERSONAL;
        }

    }
    @Override
    public String toString() {
        return value.name();
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactType // instanceof handles nulls
                && value.equals(((ContactType) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidContactType(String test) {
        return test.matches(VALIDATION_REGEX)
                && test.equalsIgnoreCase("WORK")
                || test.equalsIgnoreCase("PERSONAL");
    }
}
