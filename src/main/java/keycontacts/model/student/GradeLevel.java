package keycontacts.model.student;

/**
 * Represents a Student's grade level field in the student object.
 */
public class GradeLevel implements Comparable<GradeLevel> {

    public static final String MESSAGE_CONSTRAINTS =
            "Grade level should only contain alphanumeric characters and whitespaces, and it should not be blank";

    public final String value;

    /**
     * Constructs a {@code GradeLevel}.
     */
    public GradeLevel(String gradeLevel) {
        if (!isValidGradeLevel(gradeLevel)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.value = gradeLevel;
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

        if (!(other instanceof GradeLevel)) {
            return false;
        }

        GradeLevel otherGradeLevel = (GradeLevel) other;
        return value.equals(otherGradeLevel.value);
    }

    /**
     * Returns true if a given string is a valid grade level.
     */
    public static boolean isValidGradeLevel(String gradeLevel) {
        return gradeLevel.matches("^[a-zA-Z0-9\\s]+$");
    }

    @Override
    public int compareTo(GradeLevel o) {
        return this.toString().compareTo(o.toString());
    }
}
