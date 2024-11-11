package seedu.address.model.tag;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;

import seedu.address.model.tag.enums.Level;


/**
 * Package-private class. Represents a Tag with a Salary level.
 * This tag is used to categorize items by Salary,
 * with possible levels being "low", "medium", or "high".
 */
class SalaryTag extends Tag {

    public static final String MESSAGE_CONSTRAINTS = "Salary level needs to be either Low, Medium or High";

    private String salaryLevel;

    /**
     * Constructs a {@code SalaryTag}.
     *
     * @param tagName     A valid tag name, should preferably be "Salary" in this case
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
        assert salaryLevel != null : "salaryLevel should not be null";
        try {
            // Attempt to convert the input string to a valid Level enum value.
            Level.valueOf(salaryLevel.toUpperCase(Locale.ROOT));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Salary:" + this.salaryLevel;
    }
}
