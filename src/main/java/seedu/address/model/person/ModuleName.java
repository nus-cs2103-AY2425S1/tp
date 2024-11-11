package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's module name in the address book.
 */
public class ModuleName {

    public static final String MESSAGE_CONSTRAINTS = "Module name should consist of:\n"
            + "- 2 to 4 letters (eg. 'CS'),\n"
            + "- followed by exactly 4 digits (eg. '1231'),\n"
            + "- optionally ending with either a single letter (eg. 'S') or a dash and one letter (eg. '-T').\n"
            + "Examples of valid module names: CS1231, CS1231S, CS2103-T";

    public static final String VALIDATION_REGEX = "^([A-Za-z]{2,4}\\d{4}([A-Za-z]|-[A-Za-z])?)$";

    private final String moduleName;

    /**
     * Constructs a {@code ModuleName}.
     *
     * @param moduleName A valid ModuleName.
     */
    public ModuleName(String moduleName) {
        requireNonNull(moduleName);
        checkArgument(isValidModName(moduleName), MESSAGE_CONSTRAINTS);
        this.moduleName = moduleName.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid ModuleName.
     */
    public static boolean isValidModName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return moduleName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleName)) {
            return false;
        }

        ModuleName otherName = (ModuleName) other;
        return moduleName.equals(otherName.moduleName);
    }

    public String getModuleName() {
        return this.moduleName;
    }
}
