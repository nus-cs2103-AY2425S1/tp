package seedu.address.model.tag;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;


/**
 * Represents a Tag with a Salary level.
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
    public SalaryTag(String tagName, String salaryLevel) throws IllegalArgumentException {
        super(tagName);
        checkArgument(isValidSalary(salaryLevel), MESSAGE_CONSTRAINTS);
        this.salaryLevel = salaryLevel;
    }

    /**
     * Returns true if the Salary level is valid.
     * Valid levels are "low", "medium", or "high".
     *
     * @param salaryLevel The Salary level to do validation.
     * @return true if the Salary level is "low", "medium", or "high".
     */
    public static boolean isValidSalary(String salaryLevel) {
        String salaryLevelLowerCase = salaryLevel.toLowerCase(Locale.ROOT);
        boolean salaryLevelIsValid = salaryLevelLowerCase.equals("low")
                || salaryLevelLowerCase.equals("medium")
                || salaryLevelLowerCase.equals("high");

        return salaryLevelIsValid;
    }

    @Override
    public String toString() {
        return "Salary: " + this.salaryLevel;
    }
}
