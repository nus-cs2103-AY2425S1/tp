package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s assigned {@code Tier} starts with a specified String.
 */
public class TierStartsWithSubstringPredicate implements Predicate<Person> {
    protected final String substring;

    public TierStartsWithSubstringPredicate(String substring) {
        this.substring = substring.toUpperCase();
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.startsWithSubstringIgnoreCase(person.getTier().toParsableString(), substring);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TierStartsWithSubstringPredicate)) {
            return false;
        }

        TierStartsWithSubstringPredicate otherNameContainsSubstringPredicate = (TierStartsWithSubstringPredicate) other;
        return substring.equals(otherNameContainsSubstringPredicate.substring);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("substring", substring).toString();
    }
}
