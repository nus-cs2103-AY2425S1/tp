package seedu.address.model.client.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Client;

/**
 * Tests that a {@code Client}'s {@code Phone} contains a specified substring.
 */
public class PhoneContainsSubstringPredicate extends ContainsSubstringPredicate {

    public PhoneContainsSubstringPredicate(String substring) {
        super(substring);
    }

    @Override
    public boolean test(Client client) {
        return StringUtil.containsSubstringIgnoreCase(client.getPhone().value, substring);
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
