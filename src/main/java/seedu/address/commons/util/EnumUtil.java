package seedu.address.commons.util;

/**
 * Utility class for working with Enum types.
 */
public class EnumUtil {

    /**
     * Checks if a given string matches any of the constant names in the specified Enum class.
     * The comparison is case-insensitive.
     *
     * @param <R> The type of the Enum being checked.
     * @param test The string to check for in the Enum's constants.
     * @param enumtype The Enum class to search within.
     * @return {@code true} if the provided string matches any Enum constant name (case-insensitive),
     *         {@code false} otherwise.
     * @throws IllegalArgumentException if {@code enumtype} is null or doesn't represent an Enum type.
     */
    public static <R extends Enum<R>> boolean inEnum(String test, Class<R> enumtype) {
        for (Enum<R> c : enumtype.getEnumConstants()) {
            if (c.name().equalsIgnoreCase(test)) {
                return true;
            }
        }

        return false;
    }
}
