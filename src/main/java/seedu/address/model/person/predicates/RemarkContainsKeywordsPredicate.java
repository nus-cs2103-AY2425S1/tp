package seedu.address.model.person.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Remark} matches any of the keywords given.
 */
public class RemarkContainsKeywordsPredicate extends ContainsKeywordsPredicate {
    public RemarkContainsKeywordsPredicate(String keyword) {
        super(keyword);
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsSubstringIgnoreCase(person.getRemark().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkContainsKeywordsPredicate)) {
            return false;
        }

        RemarkContainsKeywordsPredicate otherRemarkContainsKeywordsPredicate = (RemarkContainsKeywordsPredicate) other;
        return keyword.equals(otherRemarkContainsKeywordsPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
