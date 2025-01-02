package seedu.address.model.client.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Client;

/**
 * Tests that a {@code Client}'s assigned {@code Status} starts with a specified String.
 */
public class StatusStartsWithSubstringPredicate implements Predicate<Client> {
    protected final String substring;

    public StatusStartsWithSubstringPredicate(String substring) {
        this.substring = substring.toUpperCase();
    }

    @Override
    public boolean test(Client client) {
        return StringUtil.startsWithSubstringIgnoreCase(client.getStatus().getValue(), substring);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatusStartsWithSubstringPredicate)) {
            return false;
        }

        StatusStartsWithSubstringPredicate otherStatusContainsSubstringPredicate =
                (StatusStartsWithSubstringPredicate) other;
        return substring.equals(otherStatusContainsSubstringPredicate.substring);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("substring", substring).toString();
    }
}
