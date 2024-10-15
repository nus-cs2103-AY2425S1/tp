package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

public class EmergencyContactName {

    public static final String MESSAGE_CONSTRAINTS =
            "Emergency contact names should only contain alphanumeric characters and spaces, " +
                    "and it can be blank if no contact is provided.";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String fullName;


    /**
     * Constructs an EmergencyContactName
     * @param name
     */
    public EmergencyContactName(String name) {
        checkArgument(isValidEmergencyContactName(name), MESSAGE_CONSTRAINTS);
        fullName = name;

    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidEmergencyContactName(String name) {
        return name == null || name.isEmpty() || name.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmergencyContactName)) {
            return false;
        }

        EmergencyContactName otherName = (EmergencyContactName) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
