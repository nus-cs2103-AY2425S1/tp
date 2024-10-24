package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the tutorial class a Person has attended.
 * Guarantees: immutable; tutorial is valid as declared in {@link #isValidTutorial(String)}
 */
public class Tutorial {
    public static final String MESSAGE_CONSTRAINTS =
            "Tutorial number should be between 1 to 12 only.";
    public static final String MESSAGE_INVALID_FORMAT = "Tutorial format should be: \n"
            + "1) A positive number between 1-12 \n"
            + "2) A list of numbers eg. [1,3,5] \n"
            + "3) A range of two numbers eg. 3-6";
    public static final String VALIDATION_REGEX = "([1-9]|1[0-2])";

    public final String tutorial;

    /**
     * Constructs a {@code Tutorial}.
     *
     * @param tutorial A valid tutorial number.
     */
    public Tutorial(String tutorial) {
        requireNonNull(tutorial);
        checkArgument(isValidTutorial(tutorial), MESSAGE_CONSTRAINTS);
        this.tutorial = tutorial;
    }

    /**
     * Returns true if a given string is a valid tutorial number.
     */
    public static boolean isValidTutorial(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tutorial)) {
            return false;
        }

        Tutorial otherTutorial = (Tutorial) other;
        return tutorial.equals(otherTutorial.tutorial);
    }

    @Override
    public int hashCode() {
        return tutorial.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tutorial + ']';
    }

    public String getTutorialNumber() {
        return tutorial;
    }
}
