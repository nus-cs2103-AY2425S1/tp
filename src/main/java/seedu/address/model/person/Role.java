package seedu.address.model.person;

/**
 * Represents the various types of roles that a person can have in the system.
 * Provides utility methods for converting a string into a Role and for validating if a string is a valid Role.
 */
public enum Role {
    VOLUNTEER(Volunteer.class),
    DONOR(Donor.class),
    PARTNER(Partner.class),
    PERSON(Person.class);

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid role. Valid roles are: Volunteer, Donor, Partner, Person..";

    private final Class<? extends Person> relatedClass;

    /**
     * Constructs a {@code Role}.
     *
     * @param relatedClass  The class associated with the role.
     */
    Role(Class<? extends Person> relatedClass) {
        this.relatedClass = relatedClass;
    }

    /**
     * Returns the Role corresponding to the given string.
     * Throws an IllegalArgumentException if no matching Role is found.
     */
    public static Role fromString(String role) {
        switch (role.toLowerCase()) {
        case "volunteer":
            return VOLUNTEER;
        case "donor":
            return DONOR;
        case "partner":
            return PARTNER;
        case "person":
            return PERSON;
        default:
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    /**
     * Returns true if the given string is a valid role.
     * The string is case-insensitive.
     *
     * @param test The string to test.
     * @return True if the test string corresponds to a valid role, false otherwise.
     */
    public static boolean isValidRole(String test) {
        return test.equalsIgnoreCase("volunteer")
                || test.equalsIgnoreCase("donor")
                || test.equalsIgnoreCase("partner")
                || test.equalsIgnoreCase("person");
    }

    /**
     * Returns the lowercase string representation of the enum.
     *
     * @return The name of the role in lowercase.
     */
    public String toLowerCaseString() {
        return toString().toLowerCase();
    }

    /**
     * Returns the class related to this role.
     *
     * @return The related class.
     */
    public Class<? extends Person> getRelatedClass() {
        return relatedClass;
    }
}
