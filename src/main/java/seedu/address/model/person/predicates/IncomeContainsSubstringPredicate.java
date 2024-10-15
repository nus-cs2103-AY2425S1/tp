package seedu.address.model.person.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Income} contains a specified substring.
 */
public class IncomeContainsSubstringPredicate extends ContainsSubstringPredicate {
    public IncomeContainsSubstringPredicate(String substring) {
        super(substring);
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsSubstringIgnoreCase(String.valueOf(person.getIncome().value), substring);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IncomeContainsSubstringPredicate)) {
            return false;
        }

        IncomeContainsSubstringPredicate otherIncomeContainsSubstringPredicate =
                (IncomeContainsSubstringPredicate) other;
        return substring.equals(otherIncomeContainsSubstringPredicate.substring);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("substring", substring).toString();
    }
}
