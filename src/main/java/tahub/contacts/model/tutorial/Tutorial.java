package tahub.contacts.model.tutorial;

import static tahub.contacts.commons.util.AppUtil.checkArgument;
import static tahub.contacts.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import tahub.contacts.model.course.Course;

/**
 * Represents a Tutorial in the system.
 */
public class Tutorial {

    public static final String TUTORIAL_ID_MESSAGE_CONSTRAINTS =
            "Tutorial Id should start with T, followed by exactly 2 digits between 01 and 99"
                    + "e.g., T05, T56 or T98";
    /**
     * Represents the regex pattern for validating course codes.
     * The course code should start with 'T', followed by a number from 01 to 99, as per NUS's
     * tutorial slot naming conventions.
     */
    public static final String TUTORIAL_ID_VALIDATION_REGEX = "^T([1-9][0-9]?|0[1-9])$";
    private String tutorialId;
    private Course course;

    /**
     * Initializes a new Tutorial with the given tutorial ID and associated Course.
     *
     * @param tutorialId the ID of the tutorial
     * @param course the Course associated with the tutorial
     */
    public Tutorial(String tutorialId, Course course) {
        requireAllNonNull(tutorialId, course);
        checkArgument(tutorialId.matches(TUTORIAL_ID_VALIDATION_REGEX), TUTORIAL_ID_MESSAGE_CONSTRAINTS);
        this.tutorialId = tutorialId;
        this.course = course;
    }

    /**
     * Returns the tutorial ID of this Tutorial.
     *
     * @return the tutorial ID of this Tutorial
     */
    public String getTutorialId() {
        return this.tutorialId;
    }

    /**
     * Returns the Course associated with this Tutorial.
     *
     * @return the Course associated with this Tutorial
     */
    public Course getCourse() {
        return this.course;
    }

    /**
     * Returns true if a given tutorial id is valid.
     */
    public static boolean isValidTutorialId(String test) {
        return test.matches(TUTORIAL_ID_VALIDATION_REGEX);
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
        return this.tutorialId.equals(otherTutorial.tutorialId)
                && this.course.isConflictCourse(otherTutorial.course);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.tutorialId, this.course);
    }

    Override
    public String toString() {
        return tutorialId;
    }
}
