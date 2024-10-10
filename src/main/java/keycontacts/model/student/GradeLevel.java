package keycontacts.model.student;

import java.util.regex.PatternSyntaxException;

/**
 * Represents a Student's grade level field in the student object.
 */
public class GradeLevel {

    public static final String MESSAGE_CONSTRAINTS = "Grade level should only contain alphanumeric characters "
            + "and spaces, exam board (e.g. ABRSM, Trinity) "
            + "and grade level should be separated with a space. Grade level should not exceed 50 characters, "
            + "and grade should be an integer between 1 and 12";

    public final String value;

    private String schoolLevel;
    private int grade;

    /**
     * Constructs a {@code GradeLevel}.
     * @param schoolLevel
     * @param grade
     */
    public GradeLevel(String schoolLevel, int grade) {
        this.schoolLevel = schoolLevel;
        this.grade = grade;
        this.value = schoolLevel + " " + grade;
    }

    /**
     * Constructs a {@code GradeLevel}.
     * @param gradeLevel
     * @throws PatternSyntaxException
     */
    public GradeLevel(String gradeLevel) throws PatternSyntaxException {
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
     * @param trimmedGradeLevel
     * @return
     */
    public static boolean isValidGradeLevel(String trimmedGradeLevel) {
        String[] gradeLevelParts = trimmedGradeLevel.split(" ");
        if (gradeLevelParts.length != 2) {
            return false;
        }

        String schoolLevel = gradeLevelParts[0];
        int grade;
        try {
            grade = Integer.parseInt(gradeLevelParts[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        return isValidSchoolLevel(schoolLevel) && isValidGrade(grade);
    }

    /**
     * Returns true if a given grade is valid. (1-12)
     * @param grade
     * @return
     */
    private static boolean isValidGrade(int grade) {
        return grade >= 1 && grade <= 12;
    }

    /**
     * Returns true if a given school level is valid. (less than 50 characters)
     * @param schoolLevel
     * @return
     */
    private static boolean isValidSchoolLevel(String schoolLevel) {
        return schoolLevel.length() <= 50;
    }
}
