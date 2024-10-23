package seedu.address.model.person.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} contains a specified substring.
 */
public class PhoneContainsSubstringPredicate extends ContainsSubstringPredicate {

    public PhoneContainsSubstringPredicate(String substring) {
        super(substring);
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsSubstringIgnoreCase(person.getPhone().value, substring);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneContainsSubstringPredicate)) {
            return false;
        }

        PhoneContainsSubstringPredicate otherPhoneContainsSubstringPredicate = (PhoneContainsSubstringPredicate) other;
        return substring.equals(otherPhoneContainsSubstringPredicate.substring);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("substring", substring).toString();
    }
}
