package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.List;

/**
 * Represents a Contact's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {
    public static final String ALIAS = "@";
    public static final String SON_OF = "S/O";
    public static final String DAUGHTER_OF = "D/O";
    public static final String BRACKET_PAIR = "(<Name>)";

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain letter characters and spaces, it should neither be blank nor "
                    + "contain numbers. But the following cases are still allowed: " + ALIAS + ", " + SON_OF
                    + ", " + DAUGHTER_OF + ", " + BRACKET_PAIR + ". These cases can be used, but must only "
                    + "appear once. " + ALIAS + ", " + SON_OF + ", " + DAUGHTER_OF + " must be in the middle "
                    + "of the name while " + BRACKET_PAIR + " must be after the given name (at the end of the"
                    + " full name)";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^([A-Za-z\\s]+)$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = convertToNameCase(name.trim());
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        String testInNameCase = convertToNameCase(test);
        String validSpecialCharForRegex = identifyValidSpecialChar(testInNameCase);
        boolean hasNoSpecialChar = validSpecialCharForRegex.isEmpty();
        boolean hasOpenBracket = validSpecialCharForRegex.equals("(");
        boolean hasCloseBracketAtEnd = testInNameCase.matches(".*[)]$"); // mmmmm

        if (hasNoSpecialChar) {
            return testInNameCase.matches(VALIDATION_REGEX);
        }
        if (hasOpenBracket && !hasCloseBracketAtEnd) {
            return false;
        }
        if (hasOpenBracket && hasCloseBracketAtEnd) {
            validSpecialCharForRegex = "\\("; // to be compatible with regex
            testInNameCase = testInNameCase.substring(0, test.length() - 1); // minus off the close bracket
        }
        assert validSpecialCharForRegex.equals(validSpecialCharForRegex.toUpperCase());

        List<String> nameComponents = List.of(testInNameCase.split(validSpecialCharForRegex, 2));
        return nameComponents.stream().allMatch(components -> components.matches(VALIDATION_REGEX));
    }

    private static String identifyValidSpecialChar(String nameInput) {
        List<String> specialCharactersAllowed = List.of(ALIAS, SON_OF, DAUGHTER_OF,
                BRACKET_PAIR.substring(0, 1));
        for (String word : specialCharactersAllowed) {
            if (nameInput.toUpperCase().contains(word)) {
                return word;
            }
        }
        return "";
    }

    private static String convertToNameCase(String nameInput) {
        if (nameInput.isEmpty()) {
            return nameInput;
        }
        List<String> words = List.of(nameInput.toLowerCase().split(" "));
        return words.stream()
                .map(word -> convertCase(word))
                .reduce("", (name, word) -> name + " " + word).trim();
    }

    private static String convertCase(String word) {
        if (word.equalsIgnoreCase("s/o") || word.equalsIgnoreCase("d/o")) {
            return word.toUpperCase();
        }
        if (word.substring(0, 1).equals("(")) {
            return word.substring(0, 1) + Character.toUpperCase(word.charAt(1))
                    + word.substring(2, word.length());
        }
        return Character.toUpperCase(word.charAt(0)) + word.substring(1, word.length());
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

    /**
     * Identifies if the name is the same ignoring case sensitivity
     * @param other - the other Name in question
     * @return true or false
     */
    public boolean equalsIgnoreCase(Name other) {
        if (other == null) {
            return false;
        }
        return fullName.equalsIgnoreCase(other.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

    /**
     * Compares the name of another contact in alphabetical order.
     *
     * @param otherName Name of another contact
     * @return Integer status of the comparison
     */
    public int compareTo(Name otherName) {
        return fullName.toLowerCase().compareTo(otherName.fullName.toLowerCase());
    }
}
