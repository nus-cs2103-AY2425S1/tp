package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Module in the address book
 * Guarantees: immutable; is valid as declared in {@link #isValidModule(String)}
 */
public class Module {
    public static final String MESSAGE_CONSTRAINTS = "Modules should consist of alphanumeric characters only,"
            + "and it should not be blank.";
    public static final String GRADE_CONSTRAINTS = "Grade should be a number between 0 and 100.";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    private static final int MIN_GRADE = 0;
    private static final int MAX_GRADE = 100;
    private static final int UNGRADED = -1;
    public final String module;
    private int grade;
    private boolean isGraded;
    /**
     * Constructs an {@code Module}.
     *
     * @param module a valid Module.
     */
    public Module(String module) {
        requireNonNull(module);
        checkArgument(isValidModule(module), MESSAGE_CONSTRAINTS);
        this.module = module;
        this.grade = UNGRADED;
        this.isGraded = false;
    }

    /**
     * Constructs an {@code Module} with grade.
     * @param grade a valid grade (0 - 100).
     */
    public void assignGrade(int grade) {
        checkArgument(isValidGrade(grade), GRADE_CONSTRAINTS);
        this.grade = grade;
        this.isGraded = true;
    }
    /**
     * Returns true if the module is graded.
     */
    public boolean isGraded() {
        return isGraded;
    }
    /**
     * Returns true if a given integer is a valid grade.
     * @param grade an integer.
     * @return true if grade is a valid value, between (0 - 100).
     */
    public static boolean isValidGrade(int grade) {
        return (grade >= MIN_GRADE && grade <= MAX_GRADE) || grade == UNGRADED;
    }
    public String getGrade() {
        return grade == UNGRADED ? "Ungraded" : String.valueOf(grade);
    }
    public String getModule() {
        return module;
    }
    /**
     * Returns true if a given string is a valid module.
     */
    public static boolean isValidModule(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return '[' + module + " | Grade: " + (grade == UNGRADED ? "Ungraded" : grade) + ']';
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return module.equals(otherModule.module);
    }

    @Override
    public int hashCode() {
        return module.hashCode();
    }
}
