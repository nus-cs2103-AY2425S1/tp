package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Vendor's service in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidService(String)}
 */
public class Service {
    public static final String MESSAGE_CONSTRAINTS =
            "Services should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String service;
    /**
     * Constructs a {@code Service}.
     *
     * @param service A valid service.
     */
    public Service(String service) {
        requireNonNull(service);
        checkArgument(isValidService(service), MESSAGE_CONSTRAINTS);
        this.service = service;
    }

    /**
     * Returns true if a given string is a valid service.
     */
    public static boolean isValidService(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return service;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Service)) {
            return false;
        }

        Service otherService = (Service) other;
        return service.equals(otherService.service);
    }

    @Override
    public int hashCode() {
        return service.hashCode();
    }
}
