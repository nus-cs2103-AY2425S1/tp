package seedu.internbuddy.model.company;

import java.util.List;
import java.util.function.Predicate;

import seedu.internbuddy.commons.util.StringUtil;
import seedu.internbuddy.commons.util.ToStringBuilder;

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
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(company.getName().fullName, keyword)
                || StringUtil.containsWordIgnoreCase(company.getTagsString(), keyword)
                || (company.getApplications() != null
                        && (StringUtil.containsWordIgnoreCase(company.getAppNameString(), keyword)
                        || StringUtil.containsWordIgnoreCase(company.getAppDescriptionString(), keyword))));
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
