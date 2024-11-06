package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code WorkExp} matches the specified role, company, and year.
 * The company field is required, while role and year are optional (they can be empty to match any value).
 */
public class WorkExperienceContainsKeywordsPredicate implements Predicate<Person> {
    private final String role; // Optional
    private final String company; // Required
    private final String year; // Optional
    /**
     * Constructs a {@code WorkExperienceContainsKeywordsPredicate} to filter persons based on their work experience.
     *
     * @param role The role in the work experience to match, or an empty string to match any role.
     * @param company The company in the work experience to match (this is required and cannot be empty).
     * @param year The year in the work experience to match, or an empty string to match any year.
     */
    public WorkExperienceContainsKeywordsPredicate(String role, String company, String year) {
        this.role = (role != null && !role.isEmpty()) ? role.trim().toLowerCase() : ""; // Empty means "match any role"
        this.company = company.trim().toLowerCase(); // Company is required
        this.year = (year != null && !year.isEmpty()) ? year.trim() : ""; // Empty means "match any year"
    }

    /**
     * Returns a fully formatted description of the work experience criteria in the format:
     * "worked as [role] at [company] in [year]" or variations depending on which fields are provided.
     *
     * @return a formatted string describing the work experience criteria
     */
    public String getFormattedWorkExperience() {
        StringBuilder result = new StringBuilder();

        // If a role is specified, add "as [role]"
        if (!role.isEmpty()) {
            result.append("as ").append(capitalizeFirstLetter(role)).append(" ");
        }

        // Add "at [company]" (company is always required)
        result.append("at ").append(capitalizeFirstLetter(company)).append(" ");

        // If a year is specified, add "in [year]"
        if (!year.isEmpty()) {
            result.append("in ").append(year);
        }

        return result.toString().trim(); // Trim any trailing spaces
    }



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

        if (!personCompany.equals(company)) {
            return false;
        }

        if (!role.isEmpty() && !personRole.equals(role)) {
            return false;
        }

        if (!year.isEmpty() && !personYear.equals(year)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkExperienceContainsKeywordsPredicate
                && (role.isEmpty() || role.equals(((WorkExperienceContainsKeywordsPredicate) other).role))
                && company.equals(((WorkExperienceContainsKeywordsPredicate) other).company)
                && (year.isEmpty() || year.equals(((WorkExperienceContainsKeywordsPredicate) other).year)));
    }
}
