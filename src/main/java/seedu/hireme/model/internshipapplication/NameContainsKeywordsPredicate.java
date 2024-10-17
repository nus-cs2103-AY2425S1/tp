package seedu.hireme.model.internshipapplication;

import java.util.List;
import java.util.function.Predicate;

import seedu.hireme.commons.util.StringUtil;
import seedu.hireme.commons.util.ToStringBuilder;

/**
 * Tests whether a {@code Company}'s {@code Name} contains any of the specified keywords.
 */
public class NameContainsKeywordsPredicate implements Predicate<InternshipApplication> {

    private final List<String> keywords;

    /**
     * Constructs a {@code NameContainsKeywordsPredicate} with the given list of keywords.
     *
     * @param keywords A list of keywords to check against a company's name.
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests whether the name of the {@code Company} in the given {@code InternshipApplication}
     * contains any of the specified keywords (case-insensitive).
     *
     * @param internshipApplication The internship application to be tested.
     * @return True if any of the keywords match the company name, false otherwise.
     */
    @Override
    public boolean test(InternshipApplication internshipApplication) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPrefixIgnoreCase(
                        internshipApplication.getCompanyNameValue(), keyword));
    }

    /**
     * Compares this predicate with another object for equality.
     *
     * @param other The other object to compare with.
     * @return True if both predicates contain the same keywords, false otherwise.
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
     * Returns a string representation of this predicate.
     *
     * @return A string that contains the list of keywords.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}
