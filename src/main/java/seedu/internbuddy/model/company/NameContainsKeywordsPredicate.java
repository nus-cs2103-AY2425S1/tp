package seedu.internbuddy.model.company;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.internbuddy.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Company}'s {@code Name}, tags, or application details
 * contain any of the specified keywords.
 * Keyword matching is case-insensitive and supports partial keyword matching.
 */
public class NameContainsKeywordsPredicate implements Predicate<Company> {
    private final List<String> keywords;

    /**
     * Constructs a {@code NameContainsKeywordsPredicate} with the specified keywords.
     * Keywords are converted to lowercase to enable case-insensitive matching.
     *
     * @param keywords A list of keywords to search for within a company's fields.
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        // Store keywords in lowercase to simplify case-insensitive matching
        this.keywords = keywords.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    /**
     * Evaluates this predicate on the given {@code Company}.
     * Checks if any of the specified keywords are partially present (case-insensitively)
     * in the company's name, tags, application name, or application description.
     *
     * @param company The company to evaluate the keywords against.
     * @return {@code true} if any keyword is found as a substring in the company's name, tags,
     *         application name, or application description; {@code false} otherwise.
     */
    @Override
    public boolean test(Company company) {
        // Convert the company fields to lowercase strings for case-insensitive matching
        String lowerCaseName = company.getName().fullName.toLowerCase();
        String lowerCaseTags = company.getTagsString().toLowerCase();
        String lowerCaseAppName = company.getAppNameString() != null ? company.getAppNameString().toLowerCase() : "";
        String lowerCaseAppDescription = company.getAppDescriptionString() != null
                ? company.getAppDescriptionString().toLowerCase()
                : "";

        // Check if any keyword is a substring of the fields
        return keywords.stream().anyMatch(keyword ->
                lowerCaseName.contains(keyword)
                        || lowerCaseTags.contains(keyword)
                        || lowerCaseAppName.contains(keyword)
                        || lowerCaseAppDescription.contains(keyword));
    }

    /**
     * Indicates whether another object is equal to this {@code NameContainsKeywordsPredicate}.
     * Two {@code NameContainsKeywordsPredicate} instances are equal if they have the same keywords.
     *
     * @param other The object to compare with this predicate.
     * @return {@code true} if the specified object is equal to this predicate; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    /**
     * Returns a string representation of the {@code NameContainsKeywordsPredicate}.
     * The string includes the list of keywords that this predicate checks for.
     *
     * @return A string representation of this predicate.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
