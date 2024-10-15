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
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    private static String formatName(String name) {
        String[] words = name.toLowerCase().split("\\s+");
        return formatWords(words);
    }

    /**
     * Formats an array of words by capitalizing the first letter of each word
     * and joining them into a single string.
     *
     * @param words Array of words to be formatted.
     * @return A formatted string with each word capitalized.
     */
    private static String formatWords(String[] words) {
        StringBuilder formattedWords = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                formattedWords.append(capitalizeWord(word)).append(" ");
            }
        }

        return formattedWords.toString().trim();
    }

    /**
     * Capitalizes the first letter of the word.
     *
     * @param word The word to be formatted.
     * @return Word with the first letter capitalized.
     */
    private static String capitalizeWord(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
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
