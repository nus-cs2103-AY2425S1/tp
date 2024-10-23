package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's module name in the address book.
 */
public class ModuleName {

    public static final String MESSAGE_CONSTRAINTS =
            "Module name should consist of 2-3 Alphabets then 4 numbers. Optionally, there can be one more alphabet"
            + ", one more dash followed by one more alphabet at the end of the module name.\n"
            + "e.g: CS1231S-T is a valid module name.";

    public static final String VALIDATION_REGEX = "^([A-Za-z]{2,3}\\d{4}([A-Za-z]|-[A-Za-z]|[A-Za-z]-[A-Za-z])?)?$";

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
