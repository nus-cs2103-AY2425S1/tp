package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a module code of a NUS module in the address book.
 * Guarantees: immutable
 */
public class ModuleCode {
    public static final String MESSAGE_CONSTRAINTS =
            "Module code should only contain alphanumeric characters, and it should not be blank";

    /*
     * The input should consist of only alphanumeric characters without spaces, should not contain
     * space, and should not be blank.
     */
    public static final String VALIDATION_REGEX = "[A-Za-z]+[0-9]+[A-Za-z]?";

    public final String moduleCode;

    /**
     * Constructs a {@code ModuleCode}.
     *
     * @param moduleCode A module code.
     */
    public ModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode.trim()), MESSAGE_CONSTRAINTS);
        this.moduleCode = moduleCode.trim();
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidModuleCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return moduleCode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCode)) {
            return false;
        }

        ModuleCode otherModuleCode = (ModuleCode) other;
        return moduleCode.equals(otherModuleCode.moduleCode);
    }

    @Override
    public int hashCode() {
        return moduleCode.hashCode();
    }

}
