package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Client's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = formatName(name);
    }
    /**
     * Formats a {@code String} in the natural capitalisation of names
     * i.e. only first letters are capitalised, exactly one space between words,
     * no starting/trailing spaces, and keeps Roman numerals at the end of names.
     *
     * @param name A valid name.
     */
    public static String formatName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return name;
        }

        // Split the name into words
        String[] words = name.trim().toLowerCase().split("\\s+");
        StringBuilder formattedName = new StringBuilder();

        // Process each word
        for (int i = 0; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                String word = words[i];

                // Check if it's the last word and is a Roman numeral
                if (i == words.length - 1 && isRomanNumeral(word)) {
                    formattedName.append(word.toUpperCase());
                } else {
                    // Regular name word - capitalize first letter only
                    formattedName.append(word.substring(0, 1).toUpperCase())
                            .append(word.substring(1));
                }

                // Add space if not the last word
                if (i < words.length - 1) {
                    formattedName.append(" ");
                }
            }
        }

        return formattedName.toString();
    }
    /**
     * Verifies whether a {@code String} is a Roman numeral.
     *
     * @param word Any word.
     */
    public static boolean isRomanNumeral(String word) {
        // Convert to uppercase for checking
        word = word.toUpperCase();
        return word.matches("^M*(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    }


    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
