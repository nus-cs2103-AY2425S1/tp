package seedu.address.model.person;

/**
 * Represents the role of a person in the address book.
 */
public enum Role {
    /**
     * Represents a patient in the address book.
     */
    PATIENT,

    /**
     * Represents a caregiver in the address book.
     */
    CAREGIVER;

    public static final String MESSAGE_CONSTRAINTS = "Roles should only be 'PATIENT' or 'CAREGIVER'";

    /**
     * Checks if the given string matches any of the defined roles (case-insensitive).
     *
     * @param role The role string to check.
     * @return true if the role is valid, false otherwise.
     */
    public static boolean isValidRole(String role) {
        if (role == null) {
            return false;
        }

        try {
            // Try to convert the string to a Role constant.
            Role.valueOf(role.trim().toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            // If an exception is thrown, the role is not valid.
            return false;
        }
    }

}
