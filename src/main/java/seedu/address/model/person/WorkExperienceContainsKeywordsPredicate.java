package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code WorkExp} matches the keyword given.
 */
public class WorkExperienceContainsKeywordsPredicate implements Predicate<Person> {
    private final String role; // Optional
    private final String company; // Required
    private final String year; // Optional


    /**
     * Constructs a {@code WorkExperienceContainsKeywordsPredicate} to filter persons based on their work experience.
     * The predicate checks the person's role, company, and year fields in the work experience.
     *
     * @param role The role in the work experience to match, or an empty string to match any role.
     * @param company The company in the work experience to match (this is required and cannot be empty).
     * @param year The year in the work experience to match, or an empty string to match any year.
     */
    public WorkExperienceContainsKeywordsPredicate(String role, String company, String year) {
        // Treat null or empty role and year as wildcards (they match any value)
        this.role = (role != null && !role.isEmpty()) ? role.trim().toLowerCase() : ""; // Empty means "match any role"
        this.company = company.trim().toLowerCase(); // Company is required, so no need for empty string handling
        this.year = (year != null && !year.isEmpty()) ? year.trim() : ""; // Empty means "match any year"
    }

    /**
     * Returns a string representing the role in the format "Role,Company,Year",
     * with the first letter of the role and company capitalized.
     *
     * @return a formatted string with role, company, and year information
     */
    public String getRole() {
        StringBuilder result = new StringBuilder();

        // Capitalize the first letter of role and company
        String roleCapitalized = capitalizeFirstLetter(this.role);
        String companyCapitalized = capitalizeFirstLetter(this.company);

        // Append each part to the StringBuilder
        result.append(roleCapitalized)
                .append(",")
                .append(companyCapitalized)
                .append(",")
                .append(this.year);

        return result.toString();
    }

    /**
     * Capitalizes the first letter of the input string and makes the rest lowercase.
     * If the input is null or empty, it returns the input as is.
     *
     * @param input the string to be capitalized
     * @return the input string with the first letter capitalized and the rest in lowercase
     */
    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    @Override
    public boolean test(Person person) {
        String[] workExperienceFields = person.getWorkExp().toString().split(",");

        if (workExperienceFields.length != 3) {
            return false; // Work experience does not follow the expected format
        }

        String personRole = workExperienceFields[0].trim().toLowerCase();
        String personCompany = workExperienceFields[1].trim().toLowerCase();
        String personYear = workExperienceFields[2].trim();

        // Check company (always required)
        if (!personCompany.equals(company)) {
            return false;
        }

        // Check role (if role is empty, allow any role; otherwise, role must match)
        if (!role.isEmpty() && !personRole.equals(role)) {
            return false;
        }

        // Check year (if year is empty, allow any year; otherwise, year must match)
        if (!year.isEmpty() && !personYear.equals(year)) {
            return false;
        }

        return true; // All conditions passed
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkExperienceContainsKeywordsPredicate // instanceof handles nulls
                && (role.isEmpty() || role.equals(((WorkExperienceContainsKeywordsPredicate) other).role))
                && company.equals(((WorkExperienceContainsKeywordsPredicate) other).company)
                && (year.isEmpty() || year.equals(((WorkExperienceContainsKeywordsPredicate) other).year)));
    }
}
