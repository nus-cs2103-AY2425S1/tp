package seedu.address.model.person.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;


/**
 * Tests that a {@code Person}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate extends ContainsKeywordsPredicate {
    public AddressContainsKeywordsPredicate(String keyword) {
        super(keyword);
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsSubstringIgnoreCase(person.getAddress().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressContainsKeywordsPredicate)) {
            return false;
        }

        AddressContainsKeywordsPredicate otherAddressContainsKeywordsPredicate =
                (AddressContainsKeywordsPredicate) other;
        return keyword.equals(otherAddressContainsKeywordsPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
