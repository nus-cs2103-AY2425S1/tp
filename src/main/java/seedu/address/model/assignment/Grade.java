package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;
import java.util.Optional;

/**
 * Represents an assignment's grade
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(String)}
 */
public class Grade {

    public static final String MESSAGE_CONSTRAINTS =
            "Grade should be a decimal number not between 0 and 100 (inclusive) "
            + "OR null if not graded";

    public static final String VALIDATION_REGEX = "[0-9]{1,2}\\.[0-9]*|NULL|[0-9]{1,2}|100(\\.0*)?";

    public final Optional<Double> grade;

    /**
     * Constructs a {@code Grade}
     *
     * @param grade A valid grade
     */
    public Grade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_CONSTRAINTS);
        if (grade.equals("NULL")) {
            this.grade = Optional.empty();
        } else {
            this.grade = Optional.of(Double.parseDouble(grade));
        }
    }

    /**
     * Constructs an empty {@code Grade}
     */
    private Grade() {
        this.grade = Optional.empty();
    }

    /**
     * Returns true is given string is a valid grade
     */
    public static boolean isValidGrade(String grade) {
        return grade.matches(VALIDATION_REGEX);
    }

    /**
     * Factory method to create a default {@code Grade}
     */
    public static Grade getDefault() {
        return new Grade();
    }

    @Override
    public String toString() {
        return grade.map(g -> new DecimalFormat("#.00").format(grade.get())).orElse("NULL");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Grade)) {
            return false;
        }

        Grade otherGrade = (Grade) other;
        return this.grade.equals(otherGrade.grade);
    }

}
