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

    public NameContainsKeywordsPredicate(List<String> keywords) {
        // Store keywords in lowercase to simplify case-insensitive matching
        this.keywords = keywords.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

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

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
