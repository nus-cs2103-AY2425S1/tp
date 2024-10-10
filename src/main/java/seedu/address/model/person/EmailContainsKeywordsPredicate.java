package seedu.address.model.person;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate extends ContainsKeywordsPredicate {

    public EmailContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    // checks if the person contains the keyword
    @Override
    public boolean test(Person person) {
        return super.getKeywords().stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailContainsKeywordsPredicate otherNameContainsKeywordsPredicate)) {
            return false;
        }

        return this.getKeywords().equals(otherNameContainsKeywordsPredicate.getKeywords());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", this.getKeywords()).toString();
    }
}
