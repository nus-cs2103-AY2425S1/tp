package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Module in the address book
 * Guarantees: immutable; is valid as declared in {@link #isValidModule(String)}
 */
public class Module {
    public static final String MESSAGE_CONSTRAINTS = "Modules should consist of alphanumeric characters only,"
            + "and it should not be blank.";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String module;

    /**
     * Constructs an {@code Module}.
     *
     * @param module a valid Module.
     */
    public Module(String module) {
        requireNonNull(module);
        checkArgument(isValidModule(module), MESSAGE_CONSTRAINTS);
        this.module = module;
    }

    /**
     * Returns true if a given string is a valid module.
     */
    public static boolean isValidModule(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return '[' + module + ']';
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return module.equals(otherModule.module);
    }

    @Override
    public int hashCode() {
        return module.hashCode();
    }
}
