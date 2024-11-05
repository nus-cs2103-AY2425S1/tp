package seedu.academyassist.model.person;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.commons.util.AppUtil;

/**
 * Represents subjects taken by a Person in the management system.
 */
public class Subject {

    public static final String MESSAGE_CONSTRAINTS =
            "Subjects should only be one of the following: " + SubjectEnum.getSubjectNames();

    private final SubjectEnum subjectEnum;

    /**
     * Constructs a {@code Subject}.
     *
     * @param subject A valid subject.
     */
    public Subject(String subject) {
        requireNonNull(subject);
        AppUtil.checkArgument(isValidSubject(subject), MESSAGE_CONSTRAINTS);
        this.subjectEnum = SubjectEnum.fromString(subject);
    }

    /**
     * Returns true if a given string is a valid subject name.
     */
    public static boolean isValidSubject(String test) {
        return SubjectEnum.isValidSubject(test);
    }

    @Override
    public String toString() {
        return subjectEnum.getSubjectName();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Subject)) {
            return false;
        }

        Subject otherSubject = (Subject) other;
        return subjectEnum == otherSubject.subjectEnum;
    }

    @Override
    public int hashCode() {
        return subjectEnum.hashCode();
    }
}
