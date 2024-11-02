package seedu.address.model.client.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Client;

/**
 * Tests that a {@code Client}'s {@code Email} contains a specified substring.
 */
public class EmailContainsSubstringPredicate extends ContainsSubstringPredicate {
    public EmailContainsSubstringPredicate(String substring) {
        super(substring);
    }

    @Override
    public boolean test(Client client) {
        return StringUtil.containsSubstringIgnoreCase(client.getEmail().value, substring);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailContainsSubstringPredicate)) {
            return false;
        }

        EmailContainsSubstringPredicate otherEmailContainsSubstringPredicate = (EmailContainsSubstringPredicate) other;
        return substring.equals(otherEmailContainsSubstringPredicate.substring);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("substring", substring).toString();
    }
}
