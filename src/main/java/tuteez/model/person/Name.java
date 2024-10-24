package tuteez.model.person;

import static java.util.Objects.requireNonNull;
import static tuteez.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and the following special characters: "
                    + "/, -, ', ., ,, (, ), &. It should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\s*[\\p{Alnum}][\\p{Alnum} /'.,()&-]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        assert name != null : "Name cannot be null";
        validateInput(name);
        fullName = processName(name);
    }

    private void validateInput(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    private String processName(String name) {
        String trimmedName = name.trim();
        return toTitleCase(trimmedName);
    }

    private String toTitleCase(String name) {

        String[] words = splitIntoWords(name);
        return joinWordsWithTitleCase(words);
    }

    private String[] splitIntoWords(String name) {
        return name.split("\\s+");
    }

    private String joinWordsWithTitleCase(String[] words) {
        List<String> capitalizedWords = capitalizeWords(words);
        return joinWords(capitalizedWords);
    }

    private List<String> capitalizeWords(String[] words) {
        return Arrays.stream(words)
                .filter(word -> !word.isEmpty())
                .map(this::capitalizeWord)
                .collect(Collectors.toList());
    }

    private String joinWords(List<String> words) {
        return String.join(" ", words);
    }

    private String capitalizeWord(String word) {
        return word.substring(0, 1).toUpperCase()
                + word.substring(1).toLowerCase();
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
