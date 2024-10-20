package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code income} is greater than or equal to all the values given.
 */
public class IncomePredicate implements Predicate<Person> {
    private final List<String> incomes;

    public IncomePredicate(List<String> incomes) {
        this.incomes = incomes;
    }

    @Override
    public boolean test(Person person) {
        return incomes.stream().map(Double::valueOf)
                        .anyMatch(income -> person.getIncome().getValue() <= income);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IncomePredicate)) {
            return false;
        }

        IncomePredicate otherIncomePredicate = (IncomePredicate) other;
        return incomes.equals(otherIncomePredicate.incomes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("incomes", incomes).toString();
    }
}
