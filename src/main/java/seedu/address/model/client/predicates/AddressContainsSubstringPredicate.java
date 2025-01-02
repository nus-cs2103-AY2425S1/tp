package seedu.address.model.client.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Client;


/**
 * Tests that a {@code Client}'s {@code Address} contains a specified substring.
 */
public class AddressContainsSubstringPredicate extends ContainsSubstringPredicate {
    public AddressContainsSubstringPredicate(String substring) {
        super(substring);
    }

    @Override
    public boolean test(Client client) {
        return StringUtil.containsSubstringIgnoreCase(client.getAddress().value, substring);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressContainsSubstringPredicate)) {
            return false;
        }

        AddressContainsSubstringPredicate otherAddressContainsSubstringPredicate =
                (AddressContainsSubstringPredicate) other;
        return substring.equals(otherAddressContainsSubstringPredicate.substring);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("substring", substring).toString();
    }
}
