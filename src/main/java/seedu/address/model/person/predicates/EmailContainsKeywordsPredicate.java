package seedu.address.model.person.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate extends ContainsKeywordsPredicate {
    public EmailContainsKeywordsPredicate(String keyword) {
        super(keyword);
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsSubstringIgnoreCase(person.getEmail().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailContainsKeywordsPredicate)) {
            return false;
        }

        EmailContainsKeywordsPredicate otherEmailContainsKeywordsPredicate = (EmailContainsKeywordsPredicate) other;
        return keyword.equals(otherEmailContainsKeywordsPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
