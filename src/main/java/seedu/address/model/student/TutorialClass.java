package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tutorial class ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTutorialClass(String)}
 */
public class TutorialClass {

    public static final String MESSAGE_CONSTRAINTS = "Tutorial class should be numeric and typically represents a class code.";
    public static final String VALIDATION_REGEX = "0|[1-9]\\d+"; // Numeric, can be extended to specific format

    public final String value;

    /**
     * Constructs a {@code TutorialClass}.
     *
     * @param tutorialClass A valid tutorial class code.
     */
    public TutorialClass(String tutorialClass) {
        requireNonNull(tutorialClass);
        checkArgument(isValidTutorialClass(tutorialClass), MESSAGE_CONSTRAINTS);
        this.value = tutorialClass;
    }

    /**
     * Returns true if a given string is a valid tutorial class code.
     */
    public static boolean isValidTutorialClass(String test) {
        return test.matches(VALIDATION_REGEX); // Simple numeric validation
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TutorialClass)) {
            return false;
        }

        TutorialClass otherClass = (TutorialClass) other;
        return value.equals(otherClass.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
