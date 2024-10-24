package seedu.address.model.tag;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;

import seedu.address.model.tag.enums.Level;


/**
 * Package-private class. Represents a Tag with a Salary level.
 * This tag is used to categorize items by Salary,
 * with possible levels being "low", "medium", or "high".
 */
public class SalaryTag extends Tag {

    public static final String MESSAGE_CONSTRAINTS = "Salary level needs to be either Low, Medium or High";

    private String salaryLevel;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName     A valid tag name.
     * @param salaryLevel A salaryLevel of either "low", "medium" or "high".
     */
    SalaryTag(String tagName, String salaryLevel) throws IllegalArgumentException {
        super(tagName);
        checkArgument(isValidSalary(salaryLevel), MESSAGE_CONSTRAINTS);
        this.salaryLevel = salaryLevel.toUpperCase();
    }

    /**
     * Returns true if the Salary level is valid. Checks based on a ENUM class
     * Valid levels are "low", "medium", or "high".
     *
     * @param salaryLevel The Salary level to do validation.
     * @return true if the Salary level is "low", "medium", or "high".
     */
    private static boolean isValidSalary(String salaryLevel) {
        try {
            // Attempt to convert the input string to a valid Level enum value.
            Level.valueOf(salaryLevel.toUpperCase(Locale.ROOT));
            return true;
        } catch (IllegalArgumentException e) {
            return false; // If the conversion fails, the value is invalid.
        }
    }

    @Override
    public String toString() {
        return "Salary:" + this.salaryLevel;
    }
}
