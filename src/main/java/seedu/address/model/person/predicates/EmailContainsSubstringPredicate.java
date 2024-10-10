package seedu.address.model.person.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Email} contains a specified substring.
 */
public class EmailContainsSubstringPredicate extends ContainsSubstringPredicate {
    public EmailContainsSubstringPredicate(String substring) {
        super(substring);
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsSubstringIgnoreCase(person.getEmail().value, substring);
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
