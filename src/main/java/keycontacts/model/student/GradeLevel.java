package keycontacts.model.student;

/**
 * Represents a Student's grade level field in the student object.
 */
public class GradeLevel implements Comparable<GradeLevel> {

    public static final String MESSAGE_CONSTRAINTS = "Grade level should only contain alphanumeric characters "
            + "and spaces, exam board (e.g. ABRSM, Trinity) "
            + "and grade level should be separated with a space. Exam board should not exceed 50 characters, "
            + "and grade should be an integer between 1 and 12";

    public final String value;

    private String schoolLevel;
    private int grade;

    /**
     * Constructs a {@code GradeLevel}.
     */
    public GradeLevel(String gradeLevel) {
        if (!isValidGradeLevel(gradeLevel)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        String[] gradeLevelParts = gradeLevel.split(" ");
        this.schoolLevel = gradeLevelParts[0];
        this.grade = Integer.parseInt(gradeLevelParts[1]);

        this.value = gradeLevel;
    }

    /**
     * Returns the school level of the grade level.
     * @return school level of the grade level.
     */
    public String getSchoolLevel() {
        return schoolLevel;
    }

    /**
     * Returns the grade of the grade level.
     * @return grade of the grade level.
     */
    public int getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return schoolLevel + " " + grade;
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
        return otherGradeLevel.getSchoolLevel().equals(getSchoolLevel())
                && otherGradeLevel.getGrade() == getGrade();
    }

    /**
     * Returns true if a given string is a valid grade level.
     * @return
     */
    public static boolean isValidGradeLevel(String gradeLevel) {
        return gradeLevel.matches("^[a-zA-Z0-9 ]{1,50} ([1-9]|1[0-2])$");
    }

    @Override
    public int compareTo(GradeLevel o) {
        return this.toString().compareTo(o.toString());
    }
}
