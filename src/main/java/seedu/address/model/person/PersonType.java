package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's type in the address book.
 */
public class PersonType {

    /**
     * Only types allowed
     */
    public enum Type {
        BUYER,
        SELLER;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    public static final String MESSAGE_CONSTRAINTS = "User type must be specified as only \"buyer\" or \"seller\".";

    public static final String VALIDATION_REGEX = "[a-z]+";

    public final Type value;

    /**
     * Constructs a {@code PersonType}.
     *
     * @param str A valid string that can transformed to a type.
     */
    public PersonType(String str) {
        requireNonNull(str);
        checkArgument(isValidType(str), MESSAGE_CONSTRAINTS);
        this.value = Type.valueOf(str.toUpperCase());
    }

    /**
     * Constructs a {@code PersonType}.
     *
     * @param type A valid type.
     */
    public PersonType(Type type) {
        requireNonNull(type);
        this.value = type;
    }

    /**
     * Returns true if a given string is a valid type.
     */
    public static boolean isValidType(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        for (Type aType : Type.values()) {
            if (aType.toString().equals(test)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonType)) {
            return false;
        }

        PersonType otherPersonType = (PersonType) other;
        return value.equals(otherPersonType.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
