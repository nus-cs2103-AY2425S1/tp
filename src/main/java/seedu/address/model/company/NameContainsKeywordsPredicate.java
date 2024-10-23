package seedu.address.model.company;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Company}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Company> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Company company) {
        // Collect matching keywords for the company name
        List<String> matchingNameKeywords = keywords.stream()
                .filter(keyword ->
                        StringUtil.containsWordIgnoreCase(company.getName().fullName, keyword))
                .toList();

        // Collect matching keywords for the company tags
        List<String> matchingTagKeywords = keywords.stream()
                .filter(keyword ->
                        company.getTags().stream()
                                .anyMatch(tag ->
                                        StringUtil.containsWordIgnoreCase(tag.toString(), keyword)))
                .toList();

        return !matchingNameKeywords.isEmpty() || !matchingTagKeywords.isEmpty();
    }



    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
