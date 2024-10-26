package seedu.edulog.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.commons.util.AppUtil.checkArgument;

/**
 * Denotes the description of a Lesson, which is displayed in the Calendar.
 * The description must be used by the user to tag the Lesson in a uniquely identifiable manner - i.e. the description
 * cannot have exactly matched characters, regardless of case.
 */
public class Description {
    /**
     * Denotes the maximum number of characters that a Lesson can have.
     */
    public static final Integer MAX_CHARACTER_LIMIT = 100;

    /**
     * Constrains description to be non-empty to prevent difficulties with identifying the Description later on.
     */
    public static final String DESCRIPTION_EMPTY =
        "Lesson description cannot be empty.";

    /**
     * A cap is placed on the description to prevent the Lesson description from excessively clogging the UI.
     */
    public static final String DESCRIPTION_TOO_LONG =
        "Lesson description should be at most " + MAX_CHARACTER_LIMIT + " characters long (whitespace-inclusive).";

    public final String description;

    /**
     * Initializes a Description for a Lesson, with checks to ensure that lesson descriptions fit input restrictions,
     * e.g. cannot be an empty string.
     * @param description A String that is between 1 and {@value MAX_CHARACTER_LIMIT} characters long
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(checkEmptyDescription(description), DESCRIPTION_EMPTY);
        checkArgument(checkTooLongDescription(description), DESCRIPTION_TOO_LONG);

        this.description = description;
    }

    /**
     * Checks that description is not empty, i.e. not "".
     */
    public static boolean checkEmptyDescription(String description) {
        return description.isEmpty();
    }

    /**
     * Checks if the description fits the character limit.
     */
    public static boolean checkTooLongDescription(String description) {
        return description.length() > MAX_CHARACTER_LIMIT;
    }
}
