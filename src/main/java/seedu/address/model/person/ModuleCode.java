package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a module code of a NUS module in the address book.
 * Guarantees: immutable
 */
public class ModuleCode implements Comparable<ModuleCode> {
    public static final String MESSAGE_CONSTRAINTS =
            "Module code should only contain alphanumeric characters, and it should not be blank.\n"
            + "The accepted pattern is [Alphabets][Numbers][Alphabets], where the ending [Alphabets] are optional.\n"
                    + "Note that input check is case-insensitive and program will automatically convert valid inputs\n"
                    + "to upper case letters for consistent format and easy search of module codes."
                    + "For example: MA1522, cs2106, CS1101S, is1108, CS2103t are all considered valid inputs.";

    /*
     * The input should consist of only alphanumeric characters without spaces, should not contain
     * space, and should not be blank.
     */
    public static final String VALIDATION_REGEX = "[A-Za-z]+[0-9]+[A-Za-z]*";

    public final String moduleCode;

    /**
     * Constructs a {@code ModuleCode}.
     *
     * @param moduleCode A module code.
     */
    public ModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode.trim()), MESSAGE_CONSTRAINTS);
        this.moduleCode = moduleCode.trim().toUpperCase();
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

    @Override
    public int compareTo(ModuleCode o) {
        return this.moduleCode.compareTo(o.moduleCode);
    }
}
