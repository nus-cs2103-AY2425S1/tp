package seedu.address.model.policy;

/**
 * An enum representing all possible Policy types.
 * Whenever a new type of Policy is created (i.e., FooPolicy), apart from creating the
 * FooPolicy class, this file must be updated to match the new Policy type created (i.e., FOO).
 */
public enum PolicyType {
    LIFE,
    HEALTH,
    EDUCATION;

    /**
     * Return a list of valid Policy types as a {@code PolicyType[]}.
     */
    public static PolicyType[] getValidPolicyTypes() {
        return values();
    }

    @Override
    public String toString() {
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }
}
