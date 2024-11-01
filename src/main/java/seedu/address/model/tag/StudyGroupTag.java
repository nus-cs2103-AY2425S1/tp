package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a StudyGroupTag in the address book. Guarantees: immutable; name is valid as declared in
 * {@link #isValidStudyGroupName(String)}
 */
public class StudyGroupTag {

    public static final String MESSAGE_CONSTRAINTS = "Study group names should only contain alphanumeric "
            + "characters and dashes";

    /*
     * The study group name must be alphanumeric.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}-]+";

    public final String studyGroupName;

    /**
     * Constructs a {@code StudyGroupTag}.
     *
     * @param studyGroupName A valid study group name.
     */
    public StudyGroupTag(String studyGroupName) {
        requireNonNull(studyGroupName);
        checkArgument(isValidStudyGroupName(studyGroupName), MESSAGE_CONSTRAINTS);
        this.studyGroupName = studyGroupName;
    }

    /**
     * Returns true if a given string is a valid study group name.
     */
    public static boolean isValidStudyGroupName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudyGroupTag)) {
            return false;
        }

        StudyGroupTag otherTag = (StudyGroupTag) other;
        return studyGroupName.toLowerCase().equals(otherTag.studyGroupName.toLowerCase());
    }

    @Override
    public int hashCode() {
        return studyGroupName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + studyGroupName + ']';
    }

}
