package seedu.address.model.person.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate extends ContainsKeywordsPredicate {

    public PhoneContainsKeywordsPredicate(String keyword) {
        super(keyword);
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsSubstringIgnoreCase(person.getPhone().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneContainsKeywordsPredicate)) {
            return false;
        }

        PhoneContainsKeywordsPredicate otherPhoneContainsKeywordsPredicate = (PhoneContainsKeywordsPredicate) other;
        return keyword.equals(otherPhoneContainsKeywordsPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
