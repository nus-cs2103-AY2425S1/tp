package seedu.address.model.policy;

import java.util.Arrays;

/**
 * An enum representing all possible Policy types.
 * Whenever a new type of Policy is created (i.e., FooPolicy), apart from creating the
 * FooPolicy class, this file must be updated to match the new Policy type created (i.e., FOO).
 */
public enum PolicyType {
    LIFE,
    HEALTH,
    EDUCATION;

    public static final String MESSAGE_CONSTRAINTS = "Policy can only be "
            + getValidPolicyTypesAsString() + ".";

    /**
     * Return a list of valid Policy types as a {@code PolicyType[]}.
     */
    public static PolicyType[] getValidPolicyTypes() {
        return values();
    }

    /**
     * Convert a string to a PolicyType.
     * @param type The string to convert.
     * @return The PolicyType.
     * @throws IllegalArgumentException if the string is not a valid PolicyType.
     */
    public static PolicyType fromString(String type) {
        for (PolicyType policyType : PolicyType.values()) {
            if (policyType.name().equalsIgnoreCase(type)) {
                return policyType;
            }
        }
        throw new IllegalArgumentException("Invalid policy type: " + type);
    }

    /**
     * Returns if a given String is a valid policy type.
     */
    public static boolean isValidPolicyType(String test) {
        return Arrays.stream(values())
                .map(Enum::name)
                .anyMatch(policyType -> policyType.equalsIgnoreCase(test));
    }

    /**
     * Return a nicely formated String displaying the list of valid Policy types.
     *
     * @return a string of valid Policy types.
     */
    private static String getValidPolicyTypesAsString() {
        StringBuilder result = new StringBuilder();
        PolicyType[] validPolicyTypes = PolicyType.getValidPolicyTypes();
        for (int i = 0; i < validPolicyTypes.length - 1; i++) {
            result.append(validPolicyTypes[i] + ", ");
        }
        result.append("or " + validPolicyTypes[validPolicyTypes.length - 1]);
        return result.toString();
    }

    /**
     * Convert a PolicyType to a string.
     * @return The string.
     */
    @Override
    public String toString() {
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }
}
