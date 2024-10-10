package seedu.address.model.person.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Remark} contains a specified substring.
 */
public class RemarkContainsSubstringPredicate extends ContainsSubstringPredicate {
    public RemarkContainsSubstringPredicate(String substring) {
        super(substring);
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsSubstringIgnoreCase(person.getRemark().value, substring);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkContainsSubstringPredicate)) {
            return false;
        }

        RemarkContainsSubstringPredicate otherRemarkContainsSubstringPredicate = (RemarkContainsSubstringPredicate) other;
        return substring.equals(otherRemarkContainsSubstringPredicate.substring);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("substring", substring).toString();
    }
}
