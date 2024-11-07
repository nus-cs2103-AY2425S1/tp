package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
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
    private static boolean isRomanNumeral(String word) {
        // Convert to uppercase for checking
        word = word.toUpperCase();

        // Check if the word only contains valid Roman numeral characters
        if (!word.matches("^[IVXLCDM]+$")) {
            return false;
        }

        // Additional validation rules for Roman numerals
        // Check for valid patterns and combinations
        if (word.matches(".*I{4,}.*") ||      // No more than 3 consecutive I's
                word.matches(".*V{2,}.*") ||      // No more than 1 V
                word.matches(".*X{4,}.*") ||      // No more than 3 consecutive X's
                word.matches(".*L{2,}.*") ||      // No more than 1 L
                word.matches(".*C{4,}.*") ||      // No more than 3 consecutive C's
                word.matches(".*D{2,}.*") ||      // No more than 1 D
                word.matches(".*M{4,}.*")) {      // No more than 3 consecutive M's
            return false;
        }

        // Check for invalid sequences
        if (word.matches(".*I[LCDM].*") ||    // I can only come before V or X
                word.matches(".*V[XLCDM].*") ||   // V cannot come before any larger numeral
                word.matches(".*X[CDM].*") ||     // X can only come before L or C
                word.matches(".*L[CDM].*") ||     // L cannot come before any larger numeral
                word.matches(".*D[M].*")) {       // D cannot come before M
            return false;
        }

        return true;
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
