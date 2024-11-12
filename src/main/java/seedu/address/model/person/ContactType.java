package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.StringUtil;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class ContactType {

    public static final String MESSAGE_CONSTRAINTS =
            "Contact type must be one of the following: \"Work\", \"Personal\" or \"School\"";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final ContactTypeEnum value;

    /**
     * Constructs an {@code ContactType}.
     *
     * @param contactType A valid contact type.
     */
    public ContactType(String contactType) {
        requireNonNull(contactType);

        checkArgument(isValidContactType(contactType), MESSAGE_CONSTRAINTS);

        switch(contactType.toUpperCase()) {
        case ("WORK"):
            value = ContactTypeEnum.WORK;
            break;
        case("PERSONAL"):
            value = ContactTypeEnum.PERSONAL;
            break;
        case("SCHOOL"):
            value = ContactTypeEnum.SCHOOL;
            break;
        default:
            throw new IllegalArgumentException("Incorrect contact type given");
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
        String trimmedTest = test.trim();
        return trimmedTest.matches(VALIDATION_REGEX)
                && StringUtil.isStringInEnumIgnoreCase(test, ContactTypeEnum.class);

    }
}
