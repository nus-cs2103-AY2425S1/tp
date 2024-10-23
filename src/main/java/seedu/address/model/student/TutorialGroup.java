package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;



/**
 * Represents a Student's tutorial group in teletutor.
 * Guarantees: immutable; is valid as declared in {@link #isValidTutorialGroup(String)}
 */
public class TutorialGroup {
    public static final String MESSAGE_CONSTRAINTS =
            "Tutorial group should only be in the format of a letter followed by two numbers.";

    /**
     * The first character of the tutorial group must be a letter, followed by exactly two numbers.
     */
    public static final String VALIDATION_REGEX = "[A-Za-z][0-9]{2}";

    public final String value;


    /**
     * Constructs a {@code TutorialGroup}.
     *
     * @param tutorialGroup A valid tutorial group.
     */
    public TutorialGroup(String tutorialGroup) {
        requireNonNull(tutorialGroup);
        checkArgument(isValidTutorialGroup(tutorialGroup), MESSAGE_CONSTRAINTS);
        this.value = tutorialGroup;
    }



    /**
     * Returns true if a given string is a valid tutorial group.
     */
    public static boolean isValidTutorialGroup(String test) {
        return test.matches(VALIDATION_REGEX);
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

        if (!(other instanceof TutorialGroup otherTutorialGroup)) {
            return false;
        }

        return value.equals(otherTutorialGroup.value);
    }
}
