package seedu.address.model.client.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Client;

/**
 * Tests that a {@code Client}'s {@code Job} contains a specified substring.
 */
public class JobContainsSubstringPredicate extends ContainsSubstringPredicate {
    public JobContainsSubstringPredicate(String substring) {
        super(substring);
    }

    @Override
    public boolean test(Client client) {
        return StringUtil.containsSubstringIgnoreCase(client.getJob().value, substring);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobContainsSubstringPredicate)) {
            return false;
        }

        JobContainsSubstringPredicate otherJobContainsSubstringPredicate = (JobContainsSubstringPredicate) other;
        return substring.equals(otherJobContainsSubstringPredicate.substring);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("substring", substring).toString();
    }
}
