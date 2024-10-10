package seedu.address.model.person.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} contains a specified substring.
 */
public class NameContainsSubstringPredicate extends ContainsSubstringPredicate {
    public NameContainsSubstringPredicate(String substring) {
        super(substring);
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsSubstringIgnoreCase(person.getName().fullName, substring);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsSubstringPredicate)) {
            return false;
        }

        NameContainsSubstringPredicate otherNameContainsSubstringPredicate = (NameContainsSubstringPredicate) other;
        return substring.equals(otherNameContainsSubstringPredicate.substring);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("substring", substring).toString();
    }
}
